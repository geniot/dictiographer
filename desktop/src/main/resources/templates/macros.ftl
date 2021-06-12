<#macro renderTranslations trns styleClass>
    <#list trns as trn>
    <span class="blockEntryText trn ${styleClass}"><span class="label">${trn.locale} </span>${trn.translation}</span>
    </#list>
</#macro>

<#macro renderGrammar grammar styleClass>
    <#if grammar.posKey?has_content>
        <span class="blockEntryText grammar">
            ${props.getMessage(grammar.posKey,lang)}<#if grammar.grammarNAZ?has_content><@renderNAZGrammar nazGrammar=grammar.grammarNAZ styleClass=''/>
            </#if>
        </span>
    </#if>
</#macro>


<#macro stress cont>
${cont?replace("[","<span class='stress'>")?replace("]","</span>")?replace("|","&#183;")}
</#macro>

<#macro renderIdioms idioms styleClass>
<table class="noBorder">
        <#list idioms as idiom>
        <tr>
            <td class="noBorder"><#if idiom_index==0>♦</#if></td>
            <td class="noBorder">${idiom.idioom}</td>
            <#if idiom.idiomExplanations?has_content>
                <td class="noBorder">
                    <#list idiom.idiomExplanations as idiomExplanation>
                        <#if 1<idiom.idiomExplanations?size>${idiomExplanation_index + 1})<#else>―</#if>
                        <span class="label">${idiomExplanation.meta}</span>
                    ${idiomExplanation.explanation}
                        <#if idiomExplanation.translations?has_content>
                            <@renderTranslations trns=idiomExplanation.translations styleClass=''/>
                        </#if>
                    </#list>
                </td>
            </#if>
        </tr>
        </#list>
</table>
</#macro>