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
    </style>
</head>
<body>

<#if entry.syllables?has_content>
    <span id="syllables">
        ${entry.syllables}
    </span>
</#if>


<#if entry.partOfSpeeches?has_content>
    <#list entry.partOfSpeeches as pos>
    <div class="pos_block">
            <span class="headword_block">

            </span>
    </div>
    </#list>
</#if>

</body>
</html>