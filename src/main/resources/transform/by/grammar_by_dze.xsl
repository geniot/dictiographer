<xsl:stylesheet version="1.0"
                xmlns:dictiographer="com.dictiographer.view.TransformUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="object/void[@property='grammarDZE']">
        <xsl:variable name="pos">
            <xsl:value-of select="../../object/void[@property='posKey']/string/text()"/>
        </xsl:variable>
        <xsl:variable name="nonPersonalForm">
            <xsl:value-of select="object/void[@property='nonPersonalForm']/boolean/text()"/>
        </xsl:variable>
        <xsl:variable name="finite">
            <xsl:value-of select="object/void[@property='finite']/boolean/text()"/>
        </xsl:variable>
        <xsl:variable name="infinite">
            <xsl:value-of select="object/void[@property='infinite']/boolean/text()"/>
        </xsl:variable>
        <xsl:variable name="grammarPresent">
            <xsl:value-of select="object/void[@property='presentSing1']/string/text()"/>
        </xsl:variable>

        <xsl:if test="$pos!=''">
            <div class="grammar_block">
                <xsl:value-of select="dictiographer:getProperty($lang,$pos)"/>
                <xsl:if test="$nonPersonalForm='true'">,&#160;безасабовая форма</xsl:if>
                <xsl:if test="$finite='true'">,&#160;закончанае трыванне</xsl:if>
                <xsl:if test="$infinite='true'">,&#160;незакончанае трыванне</xsl:if>
            </div>
        </xsl:if>


        <xsl:if test="$grammarPresent!=''">
        <table class="grammar_table_container">
            <tr>
                <td>
                    <table class="declination_table">
                        <tr>
                            <td colspan="3" style="text-align:center">Цяперашні час</td>
                        </tr>
                        <tr>
                            <td>Асоба</td>
                            <td>Адзіночны лік</td>
                            <td>Множны лік</td>
                        </tr>
                        <tr>
                            <td class="title_col">1</td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='presentSing1']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='presentPl1']/string/text())"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title_col">2</td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='presentSing2']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='presentPl2']/string/text())"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title_col">3</td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='presentSing3']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='presentPl3']/string/text())"/>
                            </td>
                        </tr>

                    </table>
                </td>
                <td>
                    <table class="declination_table">
                        <tr>
                            <td colspan="3" style="text-align:center">Прошлы час</td>
                        </tr>
                        <tr>
                            <td>Асоба</td>
                            <td>Адзіночны лік</td>
                            <td>Множны лік</td>
                        </tr>
                        <tr>
                            <td class="title_col">1</td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='pastSing1']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='pastPl1']/string/text())"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title_col">2</td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='pastSing2']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='pastPl2']/string/text())"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="title_col">3</td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='pastSing3']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='pastPl3']/string/text())"/>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table style="width:100%" class="declination_table">
                        <tr>
                            <td colspan="2" style="text-align:center">Загадны лад</td>
                        </tr>
                        <tr>
                            <td>Адзіночны лік</td>
                            <td>Множны лік</td>
                        </tr>
                        <tr>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='imperSing']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='imperPl']/string/text())"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td>
                    <table style="width:100%" class="declination_table">
                        <tr>
                            <td colspan="2" style="text-align:center">Трыванне</td>
                        </tr>
                        <tr>
                            <td>Закончанае</td>
                            <td>Незакончанае</td>
                        </tr>
                        <tr>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='finiteForm']/string/text())"/>
                            </td>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='infiniteForm']/string/text())"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table style="width:100%" class="declination_table">
                        <tr>
                            <td style="text-align:center">
                               Дзеепрыслоўе
                            </td>
                            </tr><tr>
                            <td>
                                <xsl:value-of disable-output-escaping="yes"
                                              select="dictiographer:addStress(object/void[@property='participle']/string/text())"/>
                            </td>
                        </tr>
                    </table>
                </td>
                <td></td>
            </tr>
        </table>
        </xsl:if>

    </xsl:template>
</xsl:stylesheet>