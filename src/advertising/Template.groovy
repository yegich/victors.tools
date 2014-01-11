package advertising

/**
 * Created by user on 11.01.14.
 */
class Template {


    List<KeywordType> types = []

    static Builder aTemplate() {
        return new Builder();
    }

    List<KeywordType> getSequence() {
        return types
    }

    static class Builder {
        def template

        private Builder() {
            template = new Template()
        }

        Builder withType(KeywordType type) {
            template.types << type
            return this
        }

        Template build() {
            return template;
        }

        Builder and() {
            return this
        }
    }
}
