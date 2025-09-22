package com.ccc.hv.qa.file.services;

import com.ccc.hv.qa.file.pojo.OnixFile;
import com.ccc.hv.qa.file.pojo.OnixProduct;
import com.ccc.hv.qa.file.pojo.OnixXPathStructure;
import com.ccc.hv.qa.logging.AllureLogger;
import com.ccc.hv.qa.utils.enums.DatePattern;
import org.dom4j.Node;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static com.ccc.hv.qa.ui.enums.ProductType.*;
import static com.ccc.hv.qa.utils.DateTimeUtils.*;
import static com.ccc.hv.qa.utils.FileOpsUtils.getRevisionDelimiter;
import static com.ccc.hv.qa.utils.StringUtils.*;

public class OnixFileService extends XmlFileService {
    private final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private static final String UNKNOWN_PRODUCT_TYPE = "UNKNOWN";
    private static final String NO_NODE = "Can't find child node: '%s'.";
    public static final String ONIX_FILE_EXTENSION = ".xml";
    public static final String WEB_OPT_PDF_ALT = "Web Opt PDF, Alt";
    public static final String WEB_OPT_PDF_ENH = "Web Opt PDF, Enh";
    private final OnixXPathStructure xPaths;


    private OnixFileService(@NotNull Path filePath) {
        super(filePath);
        this.xPaths = identifyOnixFormat();
    }

    /**
     * Getting document from a file.
     *
     * @param filePath - absolute or relative path to onix file.
     * @return current class instance (for chaining).
     */
    public static OnixFileService readOnixFile(@NotNull Path filePath) {
        return new OnixFileService(filePath);
    }

    /**
     * Update all products' title with a new unique revision number (based on date).
     *
     * @return current class instance (for chaining)
     */
    public OnixFileService updateProductsTitle() {
        List<Node> nodes = selectNodes(xPaths.getProduct());
        for (Node node : nodes) {
            String oldTitle = getSingleChildNodeValue(node, xPaths.getProductTitle());
            Objects.requireNonNull(oldTitle, "Can't find old title.");
            String newTitle = updateTextRevisionNumber(oldTitle);
            log.debug("Update product's title with: '" + newTitle + "'.");
            String titleXpath = xPaths.getProductTitle();
            Node childNode = selectChildNode(node, titleXpath);
            Objects.requireNonNull(childNode, String.format(NO_NODE, titleXpath));
            childNode.setText(newTitle);
        }

        return this;
    }

    /**
     * Update all products' 'title without prefix' with a new unique revision number (based on date).
     *
     * @return current class instance (for chaining)
     */
    public OnixFileService updateProductsTitleWithoutPrefix() {
        List<Node> nodes = selectNodes(xPaths.getProduct());
        for (Node node : nodes) {
            String oldTitleWithoutPrefix = getSingleChildNodeValue(node, xPaths.getProductTitleWithoutPrefix());
            Objects.requireNonNull(oldTitleWithoutPrefix, "Can't find old 'title without prefix'.");
            String newTitleWithoutPrefix = updateTextRevisionNumber(oldTitleWithoutPrefix);
            log.debug("Update product's 'title without prefix' with: '" + newTitleWithoutPrefix + "'.");
            String titleWithoutPrefixXpath = xPaths.getProductTitleWithoutPrefix();
            Node childNode = selectChildNode(node, titleWithoutPrefixXpath);
            Objects.requireNonNull(childNode, String.format(NO_NODE, titleWithoutPrefixXpath));
            childNode.setText(newTitleWithoutPrefix);
        }

        return this;
    }

    public OnixFileService setProductRecordSourceName(@NotNull String value, int productInexForUpdate) {
        List<Node> nodes = selectNodes(xPaths.getProduct());

        int numOfProductsInFeed = nodes.size();
        if (numOfProductsInFeed < productInexForUpdate) {
            throw new IllegalArgumentException("Can't update 'record source name' for " + productInexForUpdate +
                    " product in the feed. Onix has only " + numOfProductsInFeed + " products.");
        }

        String recSourceNameXpath = xPaths.getProductRecordSourceName();
        Node node = nodes.get(productInexForUpdate);
        Node childNode = selectChildNode(node, recSourceNameXpath);
        Objects.requireNonNull(childNode, String.format(NO_NODE, recSourceNameXpath));
        childNode.setText(value);

        return this;
    }

    /**
     * Update Record Reference for all products with unique number (based on date).
     *
     * @return current class (for chaining)
     */
    public OnixFileService updateProductsRecordReferences() {
        List<Node> nodes = selectNodes(xPaths.getProduct());
        for (Node node : nodes) {
            String oldRecRef = getSingleChildNodeValue(node, xPaths.getProductRecordReference());
            String newRecRef = generateUniqueStringBasedOnDate(oldRecRef + getRevisionDelimiter());
            log.debug("Update product's record reference with: '" + newRecRef + "'.");

            String recRefXpath = xPaths.getProductRecordReference();
            Node childNode = selectChildNode(node, recRefXpath);
            Objects.requireNonNull(childNode, String.format(NO_NODE, recRefXpath));
            childNode.setText(newRecRef);
        }

        return this;
    }

    /**
     * Update Record Reference for all products with unique number (based on date).
     *
     * @return current class (for chaining)
     */
    public OnixFileService updateProductsRecordReferences(@NotNull String suffix) {
        List<Node> nodes = selectNodes(xPaths.getProduct());
        for (Node node : nodes) {
            String oldRecRef = getSingleChildNodeValue(node, xPaths.getProductRecordReference());
            String newRecRef = oldRecRef + getRevisionDelimiter() + suffix;
            log.debug("Update product's record reference with: '" + newRecRef + "'.");

            String recRefXpath = xPaths.getProductRecordReference();
            Node childNode = selectChildNode(node, recRefXpath);
            Objects.requireNonNull(childNode, String.format(NO_NODE, recRefXpath));
            childNode.setText(newRecRef);
        }

        return this;
    }

    /**
     * Update Message Creation Date to today date
     *
     * @return current class (for chaining)
     */
    public OnixFileService updateMessageCreationDateAsToday() {
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.ONIX.toString());
        String newValue = dateFormat.format(new Date());
        selectNode(xPaths.getMessageCreationDate()).setText(newValue);

        return this;
    }

    /**
     * Update Publication Date to provided date
     *
     * @return current class (for chaining)
     */
    public OnixFileService updatePublicationDateWith(LocalDate date) {
        log.debug("Update product's Publication Date");
        String newPubDate = changeDateFormat(date, DatePattern.ONIX);
        String oldPubDate = xPaths.getProductPublicationDate();

        selectNodes(xPaths.getProduct()).forEach(node -> updateChildNodeBy(oldPubDate, newPubDate, node));

        return this;
    }


    public OnixFileService updateIsbn10() {
        log.debug("Update product's isbn (10).");
        List<Node> nodes = selectNodes(xPaths.getProduct());
        String isbn10NodeXpath = xPaths.getProductIsbn10();

        for (Node node : nodes) {
            String newIsbn10 = generateUniqueIsbn10BasedOnDate();
            updateChildNodeBy(isbn10NodeXpath, newIsbn10, node);
        }

        return this;
    }

    public OnixFileService updateIsbn13() {
        log.debug("Update product's isbn (13)");
        String isbn13NodeXpath = xPaths.getProductIsbn13();
        List<Node> nodes = selectNodes(xPaths.getProduct());

        for (Node node : nodes) {
            String newIsbn13 = generateUniqueIsbn13BasedOnDate();
            updateChildNodeBy(isbn13NodeXpath, newIsbn13, node);
        }

        return this;
    }

    /**
     * Save products from XML to the list of products
     *
     * @return list of products from onix file.
     */
    public List<OnixProduct> getProducts() {
        log.debug("Export products to external list of onix products.");

        List<OnixProduct> result = new ArrayList<>();
        List<Node> nodes = selectNodes(xPaths.getProduct());
        for (Node node : nodes) {
            final OnixProduct product = OnixProduct.builder()
                    .title(getSingleChildNodeValue(node, xPaths.getProductTitle()))
                    .titlePrefix(getSingleChildNodeValue(node, xPaths.getProductTitlePrefix()))
                    .titleWithoutPrefix(getSingleChildNodeValue(node, xPaths.getProductTitleWithoutPrefix()))
                    .type(getProductType(node))
                    .isbn10(getSingleChildNodeValue(node, xPaths.getProductIsbn10()))
                    .isbn13(getSingleChildNodeValue(node, xPaths.getProductIsbn13()))
                    .recordReference(getSingleChildNodeValue(node, xPaths.getProductRecordReference()))
                    .recordSourceName(getSingleChildNodeValue(node, xPaths.getProductRecordSourceName()))
                    .author(getSingleChildNodeValue(node, xPaths.getProductAuthor()))
                    .publisher(getSingleChildNodeValue(node, xPaths.getProductPublisher()))
                    .onSaleDate(convertOnixDateToLocalDate(getSingleChildNodeValue(node, xPaths.getProductOnSaleDate())))
                    .subject(getMultipleChildNodesValue(node, xPaths.getProductSubject()).stream().findFirst().orElse(""))
                    .imprints(getMultipleChildNodesValue(node, xPaths.getProductImprint()))
                    .publishingStatusCode(getSingleChildNodeValue(node, xPaths.getProductPublishingStatusCode()))
                    .publicationDate(convertOnixDateToLocalDate(getSingleChildNodeValue(node, xPaths.getProductPublicationDate())))
                    .idTypeName(getMultipleChildNodesValue(node, xPaths.getProductIdTypeName()))
                    .idTypeValue(getMultipleChildNodesValue(node, xPaths.getProductIdTypeValue()))
                    .notificationType(getSingleChildNodeValue(node, xPaths.getProductNotificationType()))
                    .fromCompany(getSingleChildNodeValue(node, xPaths.getProductFromCompany()))
                    .senderName(getSingleChildNodeValue(node, xPaths.getProductSenderName()))
                    .build();

            result.add(product);
        }

        log.debug("'" + result.size() + "' product(s) was(were) gotten from onix file.");
        return result;
    }

    public boolean hasOnixNode(String tagName) {
        return !selectNodes("//*[local-name()='" + tagName + "']").isEmpty();
    }

    public OnixFile saveAsNewFile() {
        Path path = saveAsNewXmlFile();
        List<OnixProduct> products = getProducts();

        return OnixFile.builder()
                .path(path)
                .products(products)
                .build();
    }


    private void updateChildNodeBy(@NotNull String relXpath, @NotNull String newValue, Node parent) {
        log.debug("Update child's node value from: '" + relXpath + "' to: '" + newValue + "'.");
        Node childNode = selectChildNode(parent, relXpath);
        Objects.requireNonNull(childNode, String.format(NO_NODE, relXpath));
        childNode.setText(newValue);
    }

    private LocalDate convertOnixDateToLocalDate(@Nullable String onixDate) {
        log.debug("Convert '" + onixDate + "' onix date to LocalDateTime.");
        if (onixDate == null) {
            return null;
        }

        //Get date part from Onix date.
        if (onixDate.contains("T")) {
            onixDate = onixDate.substring(0, onixDate.indexOf("T"));
        }

        return getLocalDateBy(DatePattern.ONIX.toString(), onixDate);
    }

    /**
     * Method defined product type based on values from: ProductForm, ProductFormDetail, ProductFormDescription,
     * EpubType, EpubTypeDescription, EditionTypeCode nodes.
     *
     * @param productNode node that represent product.
     * @return product type value.
     */
    @NotNull
    private String getProductType(@NotNull Node productNode) {
        log.debug("Recognizing product type.");

        // Product type can be recognized by single value.
        final TreeMap<String, String> singleValueDictionary = new TreeMap<>();
        singleValueDictionary.put("AA", "Audio");
        singleValueDictionary.put("AB", "Audio Cassette");
        singleValueDictionary.put("AC", "CD-Audio");
        singleValueDictionary.put("AJ", "Downloadable Audio File");
        singleValueDictionary.put("DB", "CD-ROM");
        singleValueDictionary.put("BA", PRINT.toString());
        singleValueDictionary.put("BB", PRINT.toString());
        singleValueDictionary.put("BC", PRINT.toString());
        singleValueDictionary.put("BD", PRINT.toString());
        singleValueDictionary.put("BE", PRINT.toString());
        singleValueDictionary.put("BF", PRINT.toString());
        singleValueDictionary.put("BG", PRINT.toString());
        singleValueDictionary.put("BH", PRINT.toString());
        singleValueDictionary.put("BI", PRINT.toString());
        singleValueDictionary.put("BJ", PRINT.toString());
        singleValueDictionary.put("BK", PRINT.toString());
        singleValueDictionary.put("BL", PRINT.toString());
        singleValueDictionary.put("BM", PRINT.toString());
        singleValueDictionary.put("BN", PRINT.toString());
        singleValueDictionary.put("BO", PRINT.toString());
        singleValueDictionary.put("BP", PRINT.toString());
        singleValueDictionary.put("BZ", PRINT.toString());
        singleValueDictionary.put("PC", CALENDAR.toString());
        singleValueDictionary.put("PD", "Cards");
        singleValueDictionary.put("PF", "Diary");
        singleValueDictionary.put("PH", "Kit");
        singleValueDictionary.put("PJ", "Postcard Book");
        singleValueDictionary.put("PM", "Wallet Or Folder");
        singleValueDictionary.put("PP", "Stickers");
        singleValueDictionary.put("PZ", "Other Printed Item");
        singleValueDictionary.put("VI", "DVD Video");
        singleValueDictionary.put("SF", "Mixed Media Product");
        singleValueDictionary.put("WW", "Mixed Media Product");
        singleValueDictionary.put("SA", "Multiple Copy Pack");
        singleValueDictionary.put("WX", "Multiple Copy Pack");
        singleValueDictionary.put("XD", "Counterpack");
        singleValueDictionary.put("XE", "Counterpack");
        singleValueDictionary.put("ZE", "Game");
        singleValueDictionary.put("ZJ", "Jigsaw");
        singleValueDictionary.put("ZZ", "Other Merchandise");

        // Product type can be recognized by multiple values.
        final TreeMap<String, String> multiValuesDictionary = new TreeMap<>();
        multiValuesDictionary.put("DG", EBOOK.toString());
        multiValuesDictionary.put("EA", EBOOK.toString());
        multiValuesDictionary.put("ED", EBOOK.toString());
        multiValuesDictionary.put("DG E201 029 ENH", ENHANCED_FIXED_EPUB.toString());
        multiValuesDictionary.put("EA E101 E201 ENH", ENHANCED_FIXED_EPUB.toString());
        multiValuesDictionary.put("ED E101 E201 ENH", ENHANCED_FIXED_EPUB.toString());
        multiValuesDictionary.put("DG E201 029", FIXED_LAYOUT_EPUB.toString());
        multiValuesDictionary.put("EA E101 E201", FIXED_LAYOUT_EPUB.toString());
        multiValuesDictionary.put("ED E101 E201", FIXED_LAYOUT_EPUB.toString());
        multiValuesDictionary.put("DG 029 ENH", ENHANCED_EPUB.toString());
        multiValuesDictionary.put("EA E101 ENH", ENHANCED_EPUB.toString());
        multiValuesDictionary.put("ED E101 ENH", ENHANCED_EPUB.toString());
        multiValuesDictionary.put("DG 029", EPUB.toString());
        multiValuesDictionary.put("EA E101", EPUB.toString());
        multiValuesDictionary.put("ED E101", EPUB.toString());
        multiValuesDictionary.put("DG EPUB, Alternate 029", EPUB_ALTERNATE.toString());
        multiValuesDictionary.put("EA E101 Epub, Alternate", EPUB_ALTERNATE.toString());
        multiValuesDictionary.put("ED E101 Epub, Alternate", EPUB_ALTERNATE.toString());
        multiValuesDictionary.put("DG 002 Nook Page Perfect", NOOK_PAGE_PERFECT_PDF.toString());
        multiValuesDictionary.put("EA E107 Nook Page Perfect", NOOK_PAGE_PERFECT_PDF.toString());
        multiValuesDictionary.put("ED E107 Nook Page Perfect", NOOK_PAGE_PERFECT_PDF.toString());
        multiValuesDictionary.put("DG Pub XML 002", PUB_XML.toString());
        multiValuesDictionary.put("EA E107 Pub XML", PUB_XML.toString());
        multiValuesDictionary.put("ED E107 Pub XML", PUB_XML.toString());
        multiValuesDictionary.put("DG 002 Search PDF", SEARCH_PDF.toString());
        multiValuesDictionary.put("EA E107 Search PDF", SEARCH_PDF.toString());
        multiValuesDictionary.put("ED E107 Search PDF", SEARCH_PDF.toString());
        multiValuesDictionary.put("DG 002", WEB_OPTIMIZED_PDF.toString());
        multiValuesDictionary.put("EA E107", WEB_OPTIMIZED_PDF.toString());
        multiValuesDictionary.put("ED E107", WEB_OPTIMIZED_PDF.toString());
        multiValuesDictionary.put("DG Web PDF, Alternate 002", WEB_OPT_PDF_ALT);
        multiValuesDictionary.put("EA E107 Web PDF, Alternate", WEB_OPT_PDF_ALT);
        multiValuesDictionary.put("ED E107 Web PDF, Alternate", WEB_OPT_PDF_ALT);
        multiValuesDictionary.put("DG Web PDF, Enhanced 002", WEB_OPT_PDF_ENH);
        multiValuesDictionary.put("EA E107 Web PDF, Enhanced", WEB_OPT_PDF_ENH);
        multiValuesDictionary.put("ED E107 Web PDF, Enhanced", WEB_OPT_PDF_ENH);
        multiValuesDictionary.put("DG E201 022", FIXED_LAYOUT_KINDLE.toString());
        multiValuesDictionary.put("DG E201 031", FIXED_LAYOUT_KINDLE.toString());
        multiValuesDictionary.put("EA E116 E201", FIXED_LAYOUT_KINDLE.toString());
        multiValuesDictionary.put("EA E127 E201", FIXED_LAYOUT_KINDLE.toString());
        multiValuesDictionary.put("ED E116 E201", FIXED_LAYOUT_KINDLE.toString());
        multiValuesDictionary.put("ED E127 E201", FIXED_LAYOUT_KINDLE.toString());
        multiValuesDictionary.put("DG 031", KINDLE.toString());
        multiValuesDictionary.put("EA E116", KINDLE.toString());
        multiValuesDictionary.put("ED E116", KINDLE.toString());
        multiValuesDictionary.put("DG 022 ENH", ENHANCED_MOBIPOCKET.toString());
        multiValuesDictionary.put("EA E127 ENH", ENHANCED_MOBIPOCKET.toString());
        multiValuesDictionary.put("ED E127 ENH", ENHANCED_MOBIPOCKET.toString());
        multiValuesDictionary.put("DG 022", MOBIPOCKET.toString());
        multiValuesDictionary.put("EA E127", MOBIPOCKET.toString());
        multiValuesDictionary.put("ED E127", MOBIPOCKET.toString());
        multiValuesDictionary.put("DG 044", IBOOK.toString());
        multiValuesDictionary.put("EA E141", IBOOK.toString());
        multiValuesDictionary.put("ED E141", IBOOK.toString());
        multiValuesDictionary.put("DG E201 045 ENH", ENHANCED_FIXED_EPIB.toString());
        multiValuesDictionary.put("EA E142 E201 ENH", ENHANCED_FIXED_EPIB.toString());
        multiValuesDictionary.put("ED E142 E201 ENH", ENHANCED_FIXED_EPIB.toString());
        multiValuesDictionary.put("DG E201 045", FIXED_LAYOUT_EPIB.toString());
        multiValuesDictionary.put("EA E142 E201", FIXED_LAYOUT_EPIB.toString());
        multiValuesDictionary.put("ED E142 E201", FIXED_LAYOUT_EPIB.toString());
        multiValuesDictionary.put("DG 045", FIXED_LAYOUT_EPIB.toString());
        multiValuesDictionary.put("EA E142", FIXED_LAYOUT_EPIB.toString());
        multiValuesDictionary.put("ED E142", FIXED_LAYOUT_EPIB.toString());


        List<String> productTypeSequence = new ArrayList<>();
        String code;
        if (xPaths.equals(ONIX_21_SHORT_XPATHES) || xPaths.equals(ONIX_21_LONG_XPATHES)) {
            code = getSingleChildNodeValue(productNode, xPaths.getProductForm());
            if (Arrays.asList("AA", "AB", "AC", "AJ", "DB", "DG", "BA", "BB", "BC", "BD", "BE", "BF", "BG",
                    "BH", "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BZ", "PC", "PD", "PF", "PH", "PJ", "PM",
                    "PP", "PZ", "SF", "SA", "VI", "WW", "WX", "XD", "XE", "ZE", "ZJ", "ZZ").contains(code)) {
                if (singleValueDictionary.containsKey(code)) {
                    return singleValueDictionary.get(code);
                } else {
                    productTypeSequence.add(code);
                }
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductFormDetail());
            if ("E201".equals(code)) {
                productTypeSequence.add(code);
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductFormDescription());
            if (Arrays.asList("Epub, Alternate", "Pub XML", "Web PDF, Alternate", "Web PDF, Enhanced").contains(code)) {
                productTypeSequence.add(code);
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductEpubType());
            if (Arrays.asList("029", "002", "031", "022", "044", "045").contains(code)) {
                productTypeSequence.add(code);
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductEpubTypeDescription());
            if (Arrays.asList("Nook Page Perfect", "Search PDF").contains(code)) {
                productTypeSequence.add(code);
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductEditionTypeCode());
            if ("ENH".equals(code)) {
                productTypeSequence.add(code);
            }
        } else {
            code = getSingleChildNodeValue(productNode, xPaths.getProductForm());
            if (Arrays.asList("AA", "AB", "AC", "AJ", "DB", "EA", "ED", "BA", "BB", "BC", "BD", "BE", "BF", "BG", "BH",
                    "BI", "BJ", "BK", "BL", "BM", "BN", "BO", "BP", "BZ", "PC", "PD", "PF", "PH", "PJ", "PM", "PP",
                    "PZ", "SF", "SA", "VI", "XD", "XE", "ZE", "ZJ", "ZZ").contains(code)) {
                if (singleValueDictionary.containsKey(code)) {
                    return singleValueDictionary.get(code);
                } else {
                    productTypeSequence.add(code);
                }
            }

            List<String> codes = getMultipleChildNodesValue(productNode, xPaths.getProductFormDetail());
            if (!codes.isEmpty() && Arrays.asList("E101", "E107", "E116", "E127", "E141", "E142").contains(codes.get(0))) {
                productTypeSequence.add(codes.get(0));

                if (codes.size() > 1 && "E201".equals(codes.get(1))) {
                    productTypeSequence.add(codes.get(1));
                }
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductFormDescription());
            if (Arrays.asList("Epub, Alternate", "Pub XML", "Web PDF, Alternate", "Web PDF, Enhanced",
                    "Nook Page Perfect", "Search PDF").contains(code)) {
                productTypeSequence.add(code);
            }

            code = getSingleChildNodeValue(productNode, xPaths.getProductEditionType());
            if ("ENH".equals(code)) {
                productTypeSequence.add(code);
            }
        }

        String productTypeCombined = String.join(" ", productTypeSequence);

        if (multiValuesDictionary.containsKey(productTypeCombined)) {
            return multiValuesDictionary.get(productTypeCombined);
        }

        // If product type was not recognized, check if it can be recognized without 'ENH' value at the end.
        if (productTypeCombined.endsWith(" ENH")) {
            productTypeCombined = productTypeCombined.substring(0, productTypeCombined.length() - " ENH".length());
            if (multiValuesDictionary.containsKey(productTypeCombined)) {
                return multiValuesDictionary.get(productTypeCombined);
            }
        }

        return UNKNOWN_PRODUCT_TYPE;
    }

    /**
     * Identify if Onix is in short or long format.
     */
    public OnixXPathStructure identifyOnixFormat() {
        final Optional<OnixXPathStructure> detectedFormat = Stream.of(ONIX_30_SHORT_XPATHES, ONIX_30_LONG_XPATHES,
                        ONIX_21_SHORT_XPATHES, ONIX_21_LONG_XPATHES)
                .filter(f -> !selectNodes(f.getMessageFormat()).isEmpty()).findFirst();

        if (detectedFormat.isPresent()) {
            return detectedFormat.get();
        } else {
            throw new IllegalStateException("Can't identify onix format.");
        }
    }

    //Related xPaths that represents Onix 2.1 tags in short format.
    private static final OnixXPathStructure ONIX_21_SHORT_XPATHES = OnixXPathStructure.builder()
            .messageFormat("./../*[local-name()='ONIXmessage']")
            .messageCreationDate("./*[local-name()='header']/*[local-name()='m182']")
            .product("./*[local-name()='product']")
            .productRecordReference("./*[local-name()='a001']")
            .productRecordSourceName("./*[local-name()='a197']")
            .productTitle("./*[local-name()='title']/*[local-name()='b202' and text()='01']/../*[local-name()='b203']")
            .productTitlePrefix("./*[local-name()='title']/*[local-name()='b202' and text()='01']/../*[local-name()='b030']")
            .productTitleWithoutPrefix("./*[local-name()='title']/*[local-name()='b202' and text()='01']/../*[local-name()='b031']")
            .productIsbn10("./*[local-name()='productidentifier']/*[local-name()='b221' and text()='02']/../*[local-name()='b244']")
            .productIsbn13("./*[local-name()='productidentifier']/*[local-name()='b221' and text()='15']/../*[local-name()='b244']")
            .productForm("./*[local-name()='b012']")
            .productFormDetail("./*[local-name()='b333']")
            .productFormDescription("./*[local-name()='b014']")
            .productEpubType("./*[local-name()='b211']")
            .productEpubTypeDescription("./*[local-name()='b213']")
            .productEditionTypeCode("./*[local-name()='b056']")
            .productAuthor("./*[local-name()='contributor']/*[local-name()='b034' and text()='1']/../*[local-name()='b035' and text()='A01']/../*[local-name()='b037']")
            .productPublisher("./*[local-name()='publisher']/*[local-name()='b081']")
            .productOnSaleDate("./*[local-name()='supplydetail']/*[local-name()='j143']")
            .productSubject("./*[local-name()='b064']")
            .productImprint("./*[local-name()='imprint']/*[local-name()='b079']")
            .productPublishingStatusCode("./*[local-name()='b394']")
            .productPublicationDate("./*[local-name()='b003']")
            .productIdTypeName("./*[local-name()='productidentifier']/*[local-name()='b233']")
            .productIdTypeValue("./*[local-name()='productidentifier']/*[local-name()='b244']")
            .productNotificationType("./*[local-name()='a002']")
            .productFromCompany(".//parent::ONIXmessage//*[local-name()='header']/*[local-name()='m174']")
            .productSenderName(".//parent::ONIXmessage//*[local-name()='header']/*[local-name()='m174']")
            .build();

    //Related xpathes that represents Onix 2.1 tags in long format.
    private static final OnixXPathStructure ONIX_21_LONG_XPATHES = OnixXPathStructure.builder()
            .messageFormat("./../*[local-name()='ONIXMessage']")
            .messageCreationDate("./*[local-name()='Header']/*[local-name()='SentDate']")
            .product("./*[local-name()='Product']")
            .productRecordReference("./*[local-name()='RecordReference']")
            .productRecordSourceName("./*[local-name()='RecordSourceName']")
            .productTitle("./*[local-name()='Title']/*[local-name()='TitleType' and text()='01']/../*[local-name()='TitleText']")
            .productTitlePrefix("./*[local-name()='Title']/*[local-name()='TitleType' and text()='01']/..//*[local-name()='TitlePrefix']")
            .productTitleWithoutPrefix("./*[local-name()='Title']/*[local-name()='TitleType' and text()='01']/..//*[local-name()='TitleWithoutPrefix']")
            .productIsbn10("./*[local-name()='ProductIdentifier']/*[local-name()='ProductIDType' and text()='02']/../*[local-name()='IDValue']")
            .productIsbn13("./*[local-name()='ProductIdentifier']/*[local-name()='ProductIDType' and text()='15']/../*[local-name()='IDValue']")
            .productForm("./*[local-name()='ProductForm']")
            .productFormDetail("./*[local-name()='ProductFormDetail']")
            .productFormDescription("./*[local-name()='ProductFormDescription']")
            .productEpubType("./*[local-name()='EpubType']")
            .productEpubTypeDescription("./*[local-name()='EpubTypeDescription']")
            .productEditionTypeCode("./*[local-name()='EditionTypeCode']")
            .productAuthor("./*[local-name()='Contributor']/*[local-name()='SequenceNumber' and text()='1']/../*[local-name()='ContributorRole' and text()='A01']/../*[local-name()='PersonNameInverted']")
            .productPublisher("./*[local-name()='Publisher']/*[local-name()='PublisherName']")
            .productOnSaleDate("./*[local-name()='SupplyDetail']/*[local-name()='OnSaleDate']")
            .productSubject("./*[local-name()='BASICMainSubject']")
            .productImprint("./*[local-name()='Imprint']/*[local-name()='ImprintName']")
            .productPublishingStatusCode("./*[local-name()='PublishingStatus']")
            .productPublicationDate("./*[local-name()='PublicationDate']")
            .productIdTypeName("./*[local-name()='productidentifier']/*[local-name()='b233']")
            .productIdTypeValue("./*[local-name()='productidentifier']/*[local-name()='b244']")
            .productNotificationType("./*[local-name()='NotificationType']")
            .productFromCompany(".//parent::ONIXMessage//*[local-name()='FromCompany']")
            .productSenderName(".//parent::ONIXMessage//*[local-name()='FromCompany']")
            .build();

    //Related xpathes that represents Onix 3.0 tags in short format.
    private static final OnixXPathStructure ONIX_30_SHORT_XPATHES = OnixXPathStructure.builder()
            .messageFormat("./../*[local-name()='ONIXmessage' and @release='3.0']")
            .messageCreationDate("./*[local-name()='header']/*[local-name()='x307']")
            .product("./*[local-name()='product']")
            .productRecordReference("./*[local-name()='a001']")
            .productRecordSourceName("./*[local-name()='a197']")
            .productTitle("./*[local-name()='descriptivedetail']/*[local-name()='titledetail']//*[local-name()='b202' and text()='01']/..//*[local-name()='b203']")
            .productTitlePrefix("./*[local-name()='descriptivedetail']/*[local-name()='titledetail']//*[local-name()='b202' and text()='01']/..//*[local-name()='x409' and text()='01']/..//*[local-name()='b030']")
            .productTitleWithoutPrefix("./*[local-name()='descriptivedetail']/*[local-name()='titledetail']//*[local-name()='b202' and text()='01']/..//*[local-name()='x409' and text()='01']/..//*[local-name()='b031']")
            .productIsbn10("./*[local-name()='productidentifier']/*[local-name()='b221' and text()='02']/../*[local-name()='b244']")
            .productIsbn13("./*[local-name()='productidentifier']/*[local-name()='b221' and text()='15']/../*[local-name()='b244']")
            .productForm(".//descriptivedetail/*[local-name()='b012']")
            .productFormDetail(".//descriptivedetail/*[local-name()='b333']")
            .productFormDescription(".//descriptivedetail/*[local-name()='b014']")
            .productEditionType(".//descriptivedetail/*[local-name()='x419']")
            .productAuthor("./descriptivedetail/*[local-name()='contributor']/*[local-name()='b034' and text()='1']/../*[local-name()='b037']")
            .productPublisher("./publishingdetail/*[local-name()='publisher']/*[local-name()='b081']")
            .productOnSaleDate(".//*[local-name()='x461' and text()='02']/../*[local-name()='b306']")
            .productSubject("./descriptivedetail/*[local-name()='subject']/*[local-name()='b069']")
            .productImprint("./publishingdetail/*[local-name()='imprint']/*[local-name()='b079']")
            .productPublishingStatusCode("./publishingdetail/*[local-name()='b394']")
            .productPublicationDate("./publishingdetail/*[local-name()='publishingdate']/*[local-name()='x448' and text()='01']/../*[local-name()='b306']")
            .productIdTypeName("./*[local-name()='productidentifier']/*[local-name()='b233']")
            .productIdTypeValue("./*[local-name()='productidentifier']/*[local-name()='b244']")
            .productNotificationType((".//*[local-name()='a002']"))
            .productFromCompany(".//parent::ONIXmessage//*[local-name()='x298']")
            .productSenderName((".//parent::ONIXmessage//*[local-name()='x298']"))
            .build();

    //Related xpathes that represents Onix 3.0 tags in long format.
    private static final OnixXPathStructure ONIX_30_LONG_XPATHES = OnixXPathStructure.builder()
            .messageFormat("./../*[local-name()='ONIXMessage' and @release='3.0']")
            .messageCreationDate("./*[local-name()='Header']/*[local-name()='SentDateTime']")
            .product("./*[local-name()='Product']")
            .productRecordReference("./*[local-name()='RecordReference']")
            .productRecordSourceName("./*[local-name()='RecordSourceName']")
            .productTitle("./*[local-name()='DescriptiveDetail']/*[local-name()='TitleDetail']//*[local-name()='TitleType' and text()='01']/..//*[local-name()='TitleText']")
            .productTitlePrefix("./*[local-name()='DescriptiveDetail']/*[local-name()='TitleDetail']//*[local-name()='TitleType' and text()='01']/..//*[local-name()='TitleElementLevel' and text()='01']/..//*[local-name()='TitlePrefix']")
            .productTitleWithoutPrefix("./*[local-name()='DescriptiveDetail']/*[local-name()='TitleDetail']//*[local-name()='TitleType' and text()='01']/..//*[local-name()='TitleElementLevel' and text()='01']/..//*[local-name()='TitleWithoutPrefix']")
            .productIsbn10("./*[local-name()='ProductIdentifier']/*[local-name()='ProductIDType' and text()='02']/../*[local-name()='IDValue']")
            .productIsbn13("./*[local-name()='ProductIdentifier']/*[local-name()='ProductIDType' and text()='15']/../*[local-name()='IDValue']")
            .productForm(".//*[local-name()='ProductForm']")
            .productFormDetail(".//*[local-name()='ProductFormDetail']")
            .productFormDescription(".//*[local-name()='ProductFormDescription']")
            .productEditionType(".//*[local-name()='EditionType']")
            .productAuthor("./*[local-name()='Contributor']/*[local-name()='SequenceNumber' and text()='1']/../*[local-name()='PersonNameInverted']")
            .productPublisher("./*[local-name()='Publisher']/*[local-name()='PublisherName']")
            .productOnSaleDate(".//*[local-name()='SupplyDateRole' and text()='02']/../*[local-name()='Date']")
            .productSubject("./DescriptiveDetail/*[local-name()='Subject']/*[local-name()='SubjectCode']")
            .productImprint("./PublishingDetail/*[local-name()='Imprint']/*[local-name()='ImprintName']")
            .productPublishingStatusCode(".//*[local-name()='PublishingStatus']")
            .productPublicationDate(".//*[local-name()='PublishingDetail']/*[local-name()='PublishingDate']/*[local-name()='PublishingDateRole' and text()='01']/../*[local-name()='Date']")
            .productIdTypeName("./*[local-name()='productidentifier']/*[local-name()='b233']")
            .productIdTypeValue("./*[local-name()='productidentifier']/*[local-name()='b244']")
            .productNotificationType(".//*[local-name()='NotificationType']")
            .productFromCompany(".//parent::ONIXMessage//*[local-name()='SenderName']")
            .productSenderName(".//parent::ONIXMessage//*[local-name()='SenderName']")
            .build();
}