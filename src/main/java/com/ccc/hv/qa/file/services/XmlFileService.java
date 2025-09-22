package com.ccc.hv.qa.file.services;

import com.ccc.hv.qa.logging.AllureLogger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ccc.hv.qa.utils.FileOpsUtils.*;

/**
 * Created by R0manL on 08/10/20.
 */

public class XmlFileService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    protected final Document document;
    private final Path absResourceFilePath;


    protected XmlFileService(@NotNull Path filePath) {
        this.absResourceFilePath = getAbsoluteResourceFilePath(filePath);

        try {
            SAXReader xmlReader = new SAXReader();
            this.document = xmlReader.read(this.absResourceFilePath.toString());
        } catch (DocumentException e) {
            throw new IllegalStateException("Can't read: '" + filePath + "'.\n" + e.getMessage());
        }
    }

    /**
     * Getting document from a file.
     * @param relResourcePath - relative path to the file (e.g. 'assets/smoke/ProductTypes.xml')
     * @return current class instance (for chaining)
     */
    public static XmlFileService readXmlFile(@NotNull Path relResourcePath) {
        log.debug("Read '" + relResourcePath + "' XML file.");
        return new XmlFileService(relResourcePath);
    }

    @NotNull
    public static String readXmlFileToString(@NotNull Path relResourcePath) {
        log.debug("Read '" + relResourcePath + "' XML file to String.");

        Path absResourceFilePath = getAbsoluteResourceFilePath(relResourcePath);
        String result;
        try {
            result = Files.readAllLines(absResourceFilePath, StandardCharsets.UTF_8)
                    .stream()
                    .map(Object::toString)
                    .reduce("", String::concat)
                    .replace("\t", "");
        } catch (IOException e) {
            throw new IllegalArgumentException("Can't read '" + absResourceFilePath + "' file \n" + e.getMessage());
        }

        return result;
    }

    /**
     * Method selecting child node and verify if it's unique.
     * @param parentNode - parent node
     * @param childNodeRelXPath - child node's relative (to parent) xpath.
     * @return child node.
     */
    @Nullable
    public Node selectChildNode(@NotNull Node parentNode, @NotNull String childNodeRelXPath) {
        List<Node> nodes = selectChildNodes(parentNode, childNodeRelXPath);

        if (nodes.isEmpty()) {
            return null;
        }

        if (getMultipleChildNodesValue(parentNode, childNodeRelXPath).stream().distinct().count() <= 1) {
            return nodes.get(0);
        }

        throw new IllegalArgumentException("Found multiple (" + nodes.size() + ") values that are not the same for xpath: '" + childNodeRelXPath
                + "', but found '" + nodes.size() + "': "
                + nodes.stream().map(Node::asXML).collect(Collectors.joining(" ")));
    }

    public List<Node> selectChildNodes(@NotNull Node parentNode, @NotNull String childNodeRelXPath) {
        Element parentElm;
        if (parentNode instanceof Element) {
            parentElm = (Element) parentNode;
        } else {
            throw new IllegalArgumentException("Parent node '" + parentNode.getPath() + "' has not an Element.");
        }

        return parentElm.selectNodes(childNodeRelXPath);
    }

    /**
     * Null-safe getting text from child's node.
     * @param parentNode - parent node
     * @param childNodeRelXPath - related child's node path
     * @return child node's text value (if exist), empty string if not.
     */
    @Nullable
    public String getSingleChildNodeValue(@NotNull Node parentNode, @NotNull String childNodeRelXPath) {
        Node childNode = selectChildNode(parentNode, childNodeRelXPath);
        String result = childNode != null ? childNode.getText() : null;
        log.debug("Getting value from Onix.", "Value: '" + result + "', node: '" + parentNode.getPath() + childNodeRelXPath + "'.");

        return result;
    }

    /**
     * Null-safe getting text from child's node.
     * @param parentNode - parent node
     * @param childNodeRelXPath - related child's node path
     * @return child node's text value (if exist), empty string if not.
     */
    @NotNull
    public List<String> getMultipleChildNodesValue(@NotNull Node parentNode, @NotNull String childNodeRelXPath) {
        List<String> result = new ArrayList<>();
        selectChildNodes(parentNode, childNodeRelXPath).forEach(node -> {
            String text = node != null ? node.getText() : "";
            result.add(text);
            log.debug("Get node value from Onix file.", "Value: '" + text + "', " +
                    "node: '" + parentNode.getPath() + childNodeRelXPath + "'.");
        });

        return result;
    }

    public boolean hasNode(String xPath) {
        return !selectNodes(xPath).isEmpty();
    }

    /**
     * Select list of nodes by xpath
     * @param xPath - xpath for nodes search
     * @return list of nodes.
     */
    @NotNull
    protected Node selectNode(@NotNull String xPath) {
        Node result = this.document
                .getRootElement()
                .selectSingleNode(xPath);

        if (result == null) {
            throw new IllegalStateException("Node with '" + xPath + "' does not exist.");
        }

        return result;
    }

    /**
     * Select list of nodes by xpath
     * @param xPath - xpath for nodes search
     * @return list of nodes.
     */
    protected List<Node> selectNodes(@NotNull String xPath) {
        return this.document
                .getRootElement()
                .selectNodes(xPath);
    }

    /**
     * Save all changes (in XML document) into a new file with auto-generated name and place next to the original file.
     * Example. Old file name = 9780826941862.xml, new file name = 9780826941862_200901234567.xml
     * @return absolute path to a new file with all changes.
     */
    @NotNull
    protected Path saveAsNewXmlFile() {

        Path newFilePath = addUniqueSuffixTo(this.absResourceFilePath);
        try (FileWriter out = new FileWriter(newFilePath.toString())) {
            document.write(out);
            log.debug("New file with updated content has been created.", newFilePath.toString());
        } catch (IOException e) {
            log.warn("Can't create '" + newFilePath + "' file.", e.getMessage());
        }

        return newFilePath;
    }
}
