package com.ccc.hv.qa.db.enums;


/**
 * Created by R0manL on 18/12/20.
 */

public enum AssetProcessingError {
    XPATH_EXPRESSION_PARSE_EXCEPTION("XPathExpressionException: Failure converting a node of class javax.xml.transform.stream.StreamSource: org.xml.sax.SAXParseException;"),
    SAX_EXCEPTION_INVALID_CONTENT("org.xml.sax.SAXException: cvc-complex-type.2.4.a: Invalid content was found starting with element '"),
    SAX_EXCEPTION_INVALID_NOT_COMPLETE_SUPPLY_DETAIL("org.xml.sax.SAXException: cvc-complex-type.2.4.b: The content of element 'supplydetail' is not complete."),
    RELAX_NG_APPLE_FAIL_VALIDATION("RelaxNG Apple metadata.xml failed validation"),
    OPTIMIZATION_PROBLEM_FOR_APPLE_DISTR("There was a problem with optimization ONIX for Apple distribution."),
    XPATH_EXPRESSION_INVALID_TAG("net.sf.saxon.trans.XPathException: org.xml.sax.SAXParseException; lineNumber: 2; columnNumber: 283; The element type \"replace\" must be terminated by the matching end-tag \"</replace>\".");

    private final String errorText;

    AssetProcessingError(String errorText) {
        this.errorText = errorText;
    }

    public String getErrorText() {
        return this.errorText;
    }
}
