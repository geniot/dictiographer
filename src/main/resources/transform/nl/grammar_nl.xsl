<xsl:stylesheet version="1.0"
                xmlns:dictiographer="com.dictiographer.view.TransformUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >



    <xsl:template name="grammar.nl">
        <span class="grammar_block">
            <xsl:variable name="pos">
                <xsl:value-of select="object/void[@property='posKey']/string/text()"/>
            </xsl:variable>

            <!-- zelfstandig naamwoord, displaying singular and plural -->
            <xsl:if test="$pos='znw'">
                <xsl:variable name="gender">
                    <xsl:value-of select="object/void[@property='genderKey']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="plural">
                    <xsl:value-of select="object/void[@property='plural']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="alleenMeer">
                    <xsl:value-of select="object/void[@property='alleenMeer']/boolean/text()"/>
                </xsl:variable>
                <xsl:variable name="alleenEnkel">
                    <xsl:value-of select="object/void[@property='alleenEnkel']/boolean/text()"/>
                </xsl:variable>

                <xsl:if test="$gender='m' or $gender='v' or $gender='mv'">de&#160;</xsl:if>
                <xsl:if test="$gender='m,o'">het,&#160;de&#160;</xsl:if>
                <xsl:if test="$gender='o'">het&#160;</xsl:if>

                <xsl:choose>
                    <xsl:when test="$alleenMeer!='true' and $alleenEnkel!='true'">
                        <span>
                            <xsl:attribute name="title">
                                <xsl:value-of select="dictiographer:getProperty($lang,$gender)"/>
                            </xsl:attribute>
                            <xsl:value-of select="$headword"/>
                        </span>
                        <xsl:if test="$plural!=''">
                            <xsl:text>, de&#160;</xsl:text>
                            <span title="meervoud">
                                <xsl:value-of select="$plural"/>
                            </span>
                        </xsl:if>
                    </xsl:when>
                    <xsl:when test="$alleenMeer='true'">
                        <span>
                            <xsl:attribute name="title">
                                <xsl:text>voorkomt alleen in het meervoud</xsl:text>
                            </xsl:attribute>
                            <xsl:text>de&#160;</xsl:text>
                            <xsl:value-of select="$headword"/>
                            <xsl:text>&#160;(meervoud)</xsl:text>
                        </span>
                    </xsl:when>
                    <xsl:otherwise>

                    </xsl:otherwise>
                </xsl:choose>


            </xsl:if>
            <!-- werkwoord, displaying verleden and voltooid -->
            <xsl:if test="$pos='wew'">
                <xsl:variable name="helpVerb">
                    <xsl:value-of select="object/void[@property='helpVerb']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="verbType">
                    <xsl:value-of select="object/void[@property='verbType']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="verleden">
                    <xsl:value-of select="object/void[@property='verleden']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="voltooid">
                    <xsl:value-of select="object/void[@property='voltooid']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="ookAbsoluut">
                    <xsl:value-of select="object/void[@property='ookAbsoluut']/boolean/text()"/>
                </xsl:variable>

                <xsl:if test="$verbType">
                    <span class="verbType">
                        <xsl:attribute name="title">
                            <xsl:text>werkwoordsoort</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="dictiographer:getProperty($lang,$verbType)"/>
                    </span>
                </xsl:if>

                <xsl:if test="$ookAbsoluut!=''">
                    <xsl:text>;&#160;</xsl:text>
                    <span class="alleen">
                        <xsl:text>ook absoluut</xsl:text>
                    </span>
                </xsl:if>

                <xsl:if test="$verleden">
                    <span class="verleden">
                        <xsl:attribute name="title">
                            <xsl:text>onvoltooid verleden tijd</xsl:text>
                        </xsl:attribute>
                        <xsl:text>;&#160;</xsl:text>
                        <xsl:value-of select="$verleden"/>
                    </span>
                </xsl:if>

                <xsl:if test="$voltooid">
                    <span class="voltoid">
                        <xsl:attribute name="title">
                            <xsl:text>hulpwerkwoord</xsl:text>
                        </xsl:attribute>
                        <xsl:text>,&#160;</xsl:text>
                        <xsl:value-of select="dictiographer:getProperty($lang,$helpVerb)"/>
                    </span>
                    <span class="voltoid">
                        <xsl:attribute name="title">
                            <xsl:text>voltooid deelwoord (participium)</xsl:text>
                        </xsl:attribute>
                        <xsl:text>&#160;</xsl:text>
                        <xsl:value-of select="$voltooid"/>
                    </span>
                </xsl:if>
            </xsl:if>
            <!-- bijvoegelijk naamwoord, displaying superlative and comparative -->
            <xsl:if test="$pos='bnw'">
                <xsl:variable name="comparative">
                    <xsl:value-of select="object/void[@property='comparative']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="superlative">
                    <xsl:value-of select="object/void[@property='superlative']/string/text()"/>
                </xsl:variable>
                <xsl:variable name="alleenAttr">
                    <xsl:value-of select="object/void[@property='alleenAttributief']/boolean/text()"/>
                </xsl:variable>
                <xsl:variable name="alleenPred">
                    <xsl:value-of select="object/void[@property='alleenPredikatief']/boolean/text()"/>
                </xsl:variable>

                <xsl:text>bijvoegelijk naamwoord</xsl:text>

                <xsl:if test="$comparative!=''">
                    <span class="comparative">
                        <xsl:attribute name="title">
                            <xsl:text>vergelijkende / vergrotende trap</xsl:text>
                        </xsl:attribute>
                        <xsl:text>;&#160;</xsl:text>
                        <xsl:value-of select="$comparative"/>
                    </span>
                </xsl:if>
                <xsl:if test="$superlative!=''">
                    <xsl:text>,&#160;</xsl:text>
                    <span class="superlative">
                        <xsl:attribute name="title">
                            <xsl:text>overtreffende trap</xsl:text>
                        </xsl:attribute>
                        <xsl:value-of select="$superlative"/>
                    </span>
                </xsl:if>
                <xsl:if test="$alleenAttr!=''">
                    <xsl:text>;&#160;</xsl:text>
                    <span class="alleen">
                        <xsl:text>alleen attributief</xsl:text>
                    </span>
                </xsl:if>
                <xsl:if test="$alleenPred!=''">
                    <xsl:text>;&#160;</xsl:text>
                    <span class="alleen">
                        <xsl:text>alleen predikatief</xsl:text>
                    </span>
                </xsl:if>
            </xsl:if>
            <xsl:if test="$pos='bwd'">
                <xsl:value-of select="dictiographer:getProperty($lang,object/void[@property='posKey']/string/text())"/>
            </xsl:if>
            <xsl:if test="$pos='lwd'">
                <xsl:value-of select="dictiographer:getProperty($lang,object/void[@property='posKey']/string/text())"/>
            </xsl:if>
            <xsl:if test="$pos='twl'">
                <xsl:value-of select="dictiographer:getProperty($lang,object/void[@property='posKey']/string/text())"/>
            </xsl:if>
            <xsl:if test="$pos='vnw'">
                <xsl:value-of select="dictiographer:getProperty($lang,object/void[@property='vnwKey']/string/text())"/>
                <xsl:text>&#160;</xsl:text>
                <xsl:value-of select="dictiographer:getProperty($lang,object/void[@property='posKey']/string/text())"/>
            </xsl:if>
        </span>
    </xsl:template>
</xsl:stylesheet>