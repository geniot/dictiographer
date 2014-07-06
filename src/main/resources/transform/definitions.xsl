<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        >

    <xsl:template match="void[@property='entryDefinitions']">
        <xsl:variable name="defsCount"><xsl:value-of select="number(count(array/void/object[@class='com.dictiography.client.entry.EntryDefinition']))"/></xsl:variable>
        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryDefinition']">
            <table><tr>
                <td>
                    <!-- definition number if more than 1 -->
                    <xsl:if test="$defsCount>1"><span class="defnum"><xsl:number value="position()" format="1"/></span></xsl:if></td>
                <td>
                    <div class="def_block">
                        <!-- grammar section that comes immediately after the headword -->
                        <xsl:apply-templates select="void[@property='grammar']"/>

                        <xsl:if test="void[@property='meta']"><span class="meta"><xsl:value-of select="void[@property='meta']/string/text()"/></span></xsl:if>
                        <!-- definition itself -->
                        <span class="def"><xsl:value-of select="void[@property='definition']/string/text()"/></span>

                        <!-- translation definitions -->
                        <xsl:apply-templates select = "void[@property='translations']"/>

                        <!-- hyperonyms, hyponyms, synonyms, antonyms -->
                        <xsl:if test="void[@property='hyponiems' or @property='hyperoniems' or @property='synonyms' or @property='antoniems' or @property='zie']">
                            <table class="links_block">
                                <xsl:apply-templates select = "void[@property='hyperoniems' or @property='hyponiems' or @property='synonyms' or @property='antoniems' or @property='zie']"/>
                            </table>
                        </xsl:if>


                        <!-- images -->
                        <xsl:apply-templates select = "void[@property='images']"/>

                        <!-- examples -->
                        <xsl:apply-templates select = "void[@property='examples']"/>

                        <!--idioms-->
                        <xsl:apply-templates select = "void[@property='idioms']"/>

                    </div>
                </td></tr></table>
        </xsl:for-each>

    </xsl:template>
</xsl:stylesheet>