<xsl:stylesheet version="1.0"
                xmlns:dictiographer="com.dictiographer.view.TransformUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >


    <xsl:template match="object/void[@property='grammarNAZ']">
        <xsl:variable name="pos">
            <xsl:value-of select="../../object/void[@property='posKey']/string/text()"/>
        </xsl:variable>
        <xsl:variable name="gender">
            <xsl:value-of select="object/void[@property='genderKey']/string/text()"/>
        </xsl:variable>
        <xsl:variable name="onlyPlural">
            <xsl:value-of select="object/void[@property='onlyPlural']/boolean/text()"/>
        </xsl:variable>
        <xsl:variable name="onlySingular">
            <xsl:value-of select="object/void[@property='onlySingular']/boolean/text()"/>
        </xsl:variable>


        <xsl:if test="$pos!=''">
            <span class="grammar_block">
                <xsl:value-of select="dictiographer:getProperty($lang,$pos)"/>
                <xsl:if test="$gender='m'">,&#160;мужчынскі род</xsl:if>
                <xsl:if test="$gender='v'">,&#160;жаночы род</xsl:if>
                <xsl:if test="$gender='o'">,&#160;ніякі род</xsl:if>
                <xsl:if test="$onlySingular='true'">,&#160;толькі адзіночны лік</xsl:if>
                <xsl:if test="$onlyPlural='true'">,&#160;толькі множны лік</xsl:if>
            </span>
        </xsl:if>

        <table class="grammar_table_container">
            <tr><td>
                <table class="declination_table">
                    <tr>
                        <td></td>
                        <td>Адзіночны лік</td>
                        <td>Множны лік</td>
                    </tr>
                    <tr>
                        <td class="title_col">Назоўны</td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='singular']/string/text())"/></td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='plural']/string/text())"/></td>
                    </tr>
                    <tr>
                        <td class="title_col">Родны</td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='singularR']/string/text())"/></td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='pluralR']/string/text())"/></td>
                    </tr>
                    <tr>
                        <td class="title_col">Давальны</td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='singularD']/string/text())"/></td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='pluralD']/string/text())"/></td>
                    </tr>
                    <tr>
                        <td class="title_col">Вінавальны</td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='singularV']/string/text())"/></td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='pluralV']/string/text())"/></td>
                    </tr>
                    <tr>
                        <td class="title_col">Творны</td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='singularT']/string/text())"/></td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='pluralT']/string/text())"/></td>
                    </tr>
                    <tr>
                        <td class="title_col">Месны</td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='singularM']/string/text())"/></td>
                        <td><xsl:value-of disable-output-escaping="yes" select="dictiographer:addStress(object/void[@property='pluralM']/string/text())"/></td>
                    </tr>
                </table>
            </td></tr>
        </table>




    </xsl:template>
</xsl:stylesheet>