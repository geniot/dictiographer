<#macro renderNAZGrammar nazGrammar styleClass><#if nazGrammar.genderKey?has_content>,
${props.getMessage("label.naz.gender."+nazGrammar.genderKey,lang)}</#if><#if nazGrammar.onlySingular?has_content && nazGrammar.onlySingular>,
${props.getMessage("label.naz.case.only_singular_full",lang)}</#if><#if nazGrammar.onlyPlural?has_content && nazGrammar.onlyPlural>,
${props.getMessage("label.naz.case.only_plural_full",lang)}</#if>

    <#if nazGrammar.singular?has_content>
    <table>
        <tr>
            <td></td>
            <td style="text-align: right">${props.getMessage("label.dze.singular",lang)}</td>
            <td>${props.getMessage("label.dze.plural",lang)}</td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getMessage("label.naz.case.nominative",lang)}</td>
            <td><@stress cont=nazGrammar.singular/></td>
            <td><@stress cont=nazGrammar.plural/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getMessage("label.naz.case.genitive",lang)}</td>
            <td><@stress cont=nazGrammar.singularR/></td>
            <td><@stress cont=nazGrammar.pluralR/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getMessage("label.naz.case.dative",lang)}</td>
            <td><@stress cont=nazGrammar.singularD/></td>
            <td><@stress cont=nazGrammar.pluralD/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getMessage("label.naz.case.accusative",lang)}</td>
            <td><@stress cont=nazGrammar.singularV/></td>
            <td><@stress cont=nazGrammar.pluralV/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getMessage("label.naz.case.instrumentive",lang)}</td>
            <td><@stress cont=nazGrammar.singularT/></td>
            <td><@stress cont=nazGrammar.pluralT/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getMessage("label.naz.case.locative",lang)}</td>
            <td><@stress cont=nazGrammar.singularM/></td>
            <td><@stress cont=nazGrammar.pluralM/></td>
        </tr>
    </table>
    </#if>
</#macro>