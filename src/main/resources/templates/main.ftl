<html>
<head>
    <style>
        body {
            font-family: Arial;
            padding: 5px;
        }

        .hw {
            font-size: 25px;
        }

        .stress {
            font-size: 25px;
            text-decoration: underline;
        }

        .blockEntryText {
            font-size: 16px;
            display: block;
        }

        .def {
            text-indent: 20px;
        }

        .ex {
            text-indent: 40px;
            color: gray;
        }

        .trn {
            color: #00008b;
            text-indent: 40px;
        }

        .exampleTrn {
            color: #808080;
            text-indent: 60px;
        }

        .exSource {
            font-style: italic;
        }

        .label {
            text-transform:uppercase;
            color: #a52a2a;
            font-weight: bold;
            font-size: 12px;
        }

    </style>
</head>
<body>

<#macro renderTranslations trns styleClass>
    <#list trns as trn>
    <span class="blockEntryText trn ${styleClass}"><span class="label">${trn.locale} </span>${trn.translation}</span>
    </#list>
</#macro>

<#if entry.syllables?has_content>
${entry.preparedSyllables}
</#if>

<#if entry.zie?has_content>
    <#list entry.zie as link>
        <div class="blockEntryText def"><span class="label">${props.getMessage("label.see",lang)}</span> <a href="#" class="link">${link.text}</a></div>
    </#list>
</#if>


<#if entry.partOfSpeeches?has_content>
    <#list entry.partOfSpeeches as pos>
        <#if pos.entryDefinitions?has_content>

            <#list pos.entryDefinitions as def>
            <span class="blockEntryText def"><#if 1<pos.entryDefinitions?size>${def_index + 1}. </#if>${def.definition}</span>

            <#-- definition translations -->
                <#if def.translations?has_content>
                    <@renderTranslations trns=def.translations styleClass=''/>
                </#if>

            <#-- examples -->
                <#if def.examples?has_content>
                    <#list def.examples as ex>
                    <span class="blockEntryText ex">${ex.example}

                        <#if ex.source?has_content>
                            <span class="exSource">${ex.source}</span>
                        </#if>

                        <#if ex.translations?has_content>
                            <@renderTranslations trns=ex.translations styleClass='exampleTrn'/>
                        </#if>

                        </span>
                    </#list>
                </#if>

            </#list>

        </#if>
    </#list>
</#if>

</body>
</html>