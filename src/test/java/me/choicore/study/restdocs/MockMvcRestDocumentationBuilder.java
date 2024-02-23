package me.choicore.study.restdocs;

import org.springframework.restdocs.generate.RestDocumentationGenerator;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.util.StringUtils;


public class MockMvcRestDocumentationBuilder {
    private MockMvcRestDocumentationBuilder() {
    }

    public static RestDocumentationResultHandlerBuilder identifier(String identifier) {
        return new RestDocumentationResultHandlerBuilder(identifier);
    }

    public static RestDocumentationResultHandlerBuilder withDefault() {
        return new RestDocumentationResultHandlerBuilder(RestDocumentationResultHandlerBuilder.DEFAULT_DOCUMENTATION_IDENTIFIER);
    }

    public static class RestDocumentationResultHandlerBuilder {

        public static final String DEFAULT_DOCUMENTATION_IDENTIFIER = "{class-name}/{method-name}";
        private String documentationIdentifier;
        private RequestFieldsSnippet requestFieldsSnippets;
        private ResponseFieldsSnippet responseFieldsSnippets;
        private OperationRequestPreprocessor operationRequestPreprocessor = Preprocessors.preprocessRequest(Preprocessors.prettyPrint());
        private OperationResponsePreprocessor operationResponsePreprocessor = Preprocessors.preprocessResponse(Preprocessors.prettyPrint());

        RestDocumentationResultHandlerBuilder(String identifier) {
            identifier(identifier);
        }

        public RestDocumentationResultHandlerBuilder identifier(String identifier) {
            if (!StringUtils.hasLength(identifier)) {
                throw new IllegalArgumentException("identifier must not be empty");
            }
            this.documentationIdentifier = identifier;
            return this;
        }

        public RestDocumentationResultHandlerBuilder preprocessRequest(OperationRequestPreprocessor operationRequestPreprocessor) {
            this.operationRequestPreprocessor = operationRequestPreprocessor;
            return this;
        }

        public RestDocumentationResultHandlerBuilder preprocessResponse(OperationResponsePreprocessor operationResponsePreprocessor) {
            this.operationResponsePreprocessor = operationResponsePreprocessor;
            return this;
        }

        public RestDocumentationResultHandlerBuilder requestFields(RequestFieldsSnippet requestFieldsSnippets) {
            this.requestFieldsSnippets = requestFieldsSnippets;
            return this;
        }

        public RestDocumentationResultHandlerBuilder responseFields(ResponseFieldsSnippet responseFieldsSnippets) {
            this.responseFieldsSnippets = responseFieldsSnippets;
            return this;
        }

        public RestDocumentationResultHandler build() {
            return MockMvcRestDocumentation.document(
                    documentationIdentifier,
                    operationRequestPreprocessor,
                    operationResponsePreprocessor,
                    requestFieldsSnippets,
                    responseFieldsSnippets);
        }
    }
}
