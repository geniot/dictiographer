<xsl:stylesheet version="1.0"
                xmlns:dictiography="com.dictiography.shared.ElexUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >


    <xsl:template match="object/void[@property='grammarPRY']">

        <xsl:variable name="pos">
            <xsl:value-of select="../../object/void[@property='posKey']/string/text()"/>
        </xsl:variable>
        <xsl:variable name="grammarPresent">
            <xsl:value-of select="object/void[@property='singularMN']/string/text()"/>
        </xsl:variable>


        <xsl:if test="$pos!=''">
            <span class="grammar_block">
                <xsl:value-of select="dictiography:getProperty($lang,$pos)"/>
            </span>
        </xsl:if>

        <xsl:if test="$grammarPresent!=''">
            <table class="grammar_table_container">
                <tr>
                    <td>

                        <table class="grammar_table_container">
                            <tr><td>
                                <table class="declination_table">
                                    <tr>
                                        <td style="text-align:center" colspan="4">Адзіночны лік</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>Мужчынскі</td>
                                        <td>Жаночы</td>
                                        <td>Ніякі</td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Назоўны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularMN']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularZN']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularNN']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Родны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularMR']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularZR']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularNR']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Давальны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularMD']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularZD']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularND']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Вінавальны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularMV']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularZV']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularNV']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Творны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularMT']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularZT']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularNT']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Месны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularMM']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularZM']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='singularNM']/string/text())"/></td>
                                    </tr>

                                </table>
                            </td></tr>
                        </table>

                    </td>
                    <td>

                        <table class="grammar_table_container">
                            <tr><td>
                                <table class="declination_table">
                                    <tr>
                                        <td style="text-align:center" colspan="4">Множны лік</td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td>Мужчынскі</td>
                                        <td>Жаночы</td>
                                        <td>Ніякі</td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Назоўны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralMN']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralZN']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralNN']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Родны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralMR']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralZR']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralNR']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Давальны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralMD']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralZD']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralND']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Вінавальны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralMV']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralZV']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralNV']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Творны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralMT']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralZT']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralNT']/string/text())"/></td>
                                    </tr>
                                    <tr>
                                        <td class="title_col">Месны</td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralMM']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralZM']/string/text())"/></td>
                                        <td><xsl:value-of disable-output-escaping="yes" select="dictiography:addStress(object/void[@property='pluralNM']/string/text())"/></td>
                                    </tr>

                                </table>
                            </td></tr>
                        </table>

                    </td>
                </tr>
            </table>
        </xsl:if>


    </xsl:template>
</xsl:stylesheet>