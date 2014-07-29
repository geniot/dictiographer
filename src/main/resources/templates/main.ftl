<html>
<head>
    <style>
        body {
            padding: 5px;
        }

        .hw {
            font-family: Arial;
            font-size: 25px;
        }

        .stress {
            font-family: Arial;
            font-size: 25px;
            text-decoration: underline;
        }

        .def {
            font-family: Arial;
            font-size: 12px;
            text-indent: 20px;
        }
    </style>
</head>
<body>

<#if entry.syllables?has_content>
${entry.preparedSyllables}
</#if>


<#if entry.partOfSpeeches?has_content>
    <#list entry.partOfSpeeches as pos>
    <div class="pos_block">
        <#if pos.entryDefinitions?has_content>
            <#list pos.entryDefinitions as def>
                <div class="def">${def.definition}</div>
            </#list>
        </#if>
    </div>
    </#list>
</#if>

</body>
</html>