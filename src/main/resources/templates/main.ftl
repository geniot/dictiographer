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

<span id="syllables">
    ${entry.syllables}
</span>

<#list entry.partOfSpeeches as pos>
<div class="pos_block">
        <span class="headword_block">

        </span>
</div>
</#list>

</body>
</html>