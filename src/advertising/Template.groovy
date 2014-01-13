package advertising

/**
 * Created by user on 11.01.14.
 */
class Template {


    List<KeywordType> types = []

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof Template)) return false

        Template template = (Template) o

        if (types != template.types) return false

        return true
    }

    int hashCode() {
        return (types != null ? types.hashCode() : 0)
    }

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
