<#macro renderNAZGrammar nazGrammar styleClass><#if nazGrammar.genderKey?has_content>,
${props.getString("label.naz.gender.nazGrammar.genderKey")}</#if><#if nazGrammar.onlySingular?has_content && nazGrammar.onlySingular>,
${props.getString("label.naz.case.only_singular_full")}</#if><#if nazGrammar.onlyPlural?has_content && nazGrammar.onlyPlural>,
${props.getString("label.naz.case.only_plural_full")}</#if>

    <#if nazGrammar.singular?has_content>
    <table>
        <tr>
            <td></td>
            <td style="text-align: right">${props.getString("label.dze.singular")}</td>
            <td>${props.getString("label.dze.plural")}</td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getString("label.naz.case.nominative")}</td>
            <td><@stress cont=nazGrammar.singular/></td>
            <td><@stress cont=nazGrammar.plural/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getString("label.naz.case.genitive")}</td>
            <td><@stress cont=nazGrammar.singularR/></td>
            <td><@stress cont=nazGrammar.pluralR/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getString("label.naz.case.dative")}</td>
            <td><@stress cont=nazGrammar.singularD/></td>
            <td><@stress cont=nazGrammar.pluralD/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getString("label.naz.case.accusative")}</td>
            <td><@stress cont=nazGrammar.singularV/></td>
            <td><@stress cont=nazGrammar.pluralV/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getString("label.naz.case.instrumentive")}</td>
            <td><@stress cont=nazGrammar.singularT/></td>
            <td><@stress cont=nazGrammar.pluralT/></td>
        </tr>
        <tr>
            <td style="text-align: right">${props.getString("label.naz.case.locative")}</td>
            <td><@stress cont=nazGrammar.singularM/></td>
            <td><@stress cont=nazGrammar.pluralM/></td>
        </tr>
    </table>
    </#if>
</#macro>