<html>
<head>
    <style>
        <#include "style.css">
    </style>
</head>
<body>

    <#include "macros.ftl">
    <#include "grammar_by.ftl">

<#-- headword -->
    <#if entry.syllables?has_content>
    ${entry.preparedSyllables}
    </#if>

<#-- see -->
    <#if entry.zie?has_content>
    <div class="blockEntryText def"><span class="label">${props.getMessage("label.see",lang)}</span>
        <#list entry.zie as link>
            <a href="${link.text}" class="link">${link.text}</a><#if link_index<entry.zie?size-1>, </#if>
        </#list>
    </div>
    </#if>


    <#if entry.partOfSpeeches?has_content>
        <#list entry.partOfSpeeches as pos>

        <#-- derivatives -->
            <#if pos.afleidingen?has_content>
            <div class="blockEntryText def">
                    <span class="label">${props.getMessage("label.derivatives",lang)}:</span>
                    <#list pos.afleidingen as afleiding>
                        <a href="${afleiding}" class="link">${afleiding}</a><#if afleiding_index<pos.afleidingen?size-1>, </#if>
                    </#list>
                </div>
            </#if>

            <#if pos.pronunciation?has_content>
            <div class="blockEntryText pron">[${pos.pronunciation}]</div>
            </#if>

        <#-- pos grammar -->
            <#if pos.grammar?has_content>
                <@renderGrammar grammar=pos.grammar styleClass='posGrammar'/>
            </#if>

        <#-- definitions -->
            <#if pos.entryDefinitions?has_content>

                <#list pos.entryDefinitions as def>
                <span class="blockEntryText def"><#if 1<pos.entryDefinitions?size>${def_index + 1}
                    . </#if>${def.definition}</span>

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


    <#if entry.idioms?has_content>
        <@renderIdioms idioms=entry.idioms styleClass=''/>
    </#if>

</body>
</html>