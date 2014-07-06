<xsl:stylesheet version="1.0"
                xmlns:dictiography="com.dictiography.shared.ElexUtils"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template
            match="void[@property='hyperoniems' or @property='hyponiems' or @property='synonyms' or @property='antoniems' or @property='zie' or @property='afleidingen']">

            <xsl:if test="@property='hyperoniems'">
                <tr>
                    <td class="lnk_head">
                        <span class="lnk_head"><xsl:value-of select="dictiography:getProperty($lang,'label.hyperonym')"/>:&#160;</span>
                    </td>
                    <td class="lnk">
                        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryLink']">
                            <a href="#" class="lnk">
                                <xsl:value-of select="void[@property='text']/string/text()"/>
                            </a>
                            <xsl:if test="position() != last()">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>
            <xsl:if test="@property='hyponiems'">

                <xsl:variable name="hypCount">
                    <xsl:value-of
                            select="number(count(array/void/object[@class='com.dictiography.client.entry.EntryLink']))"/>
                </xsl:variable>
                <tr>
                    <td class="lnk_head">
                        <span class="lnk_head">
                            <xsl:if test="$hypCount=1"><xsl:value-of select="dictiography:getProperty($lang,'label.hyponym')"/></xsl:if>
                            <xsl:if test="$hypCount>1"><xsl:value-of select="dictiography:getProperty($lang,'label.hyponyms')"/></xsl:if>:&#160;
                        </span>
                    </td>
                    <td class="lnk">
                        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryLink']">
                            <a href="#" class="lnk">
                                <xsl:value-of select="void[@property='text']/string/text()"/>
                            </a>
                            <xsl:if test="position() != last()">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>

            <xsl:if test="@property='synonyms'">
                <xsl:variable name="synCount">
                    <xsl:value-of
                            select="number(count(array/void/object[@class='com.dictiography.client.entry.EntryLink']))"/>
                </xsl:variable>
                <tr>
                    <td class="lnk_head">
                        <span class="lnk_head">
                            <xsl:if test="$synCount=1"><xsl:value-of select="dictiography:getProperty($lang,'label.synonym')"/></xsl:if>
                            <xsl:if test="$synCount>1"><xsl:value-of select="dictiography:getProperty($lang,'label.synonyms')"/></xsl:if>:&#160;
                        </span>
                    </td>
                    <td class="lnk">
                        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryLink']">
                            <a href="#" class="lnk">
                                <xsl:value-of select="void[@property='text']/string/text()"/>
                            </a>
                            <xsl:if test="position() != last()">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>

            <xsl:if test="@property='antoniems'">
                <xsl:variable name="antCount">
                    <xsl:value-of
                            select="number(count(array/void/object[@class='com.dictiography.client.entry.EntryLink']))"/>
                </xsl:variable>
                <tr>
                    <td class="lnk_head">
                        <span class="lnk_head">
                            <xsl:if test="$antCount=1"><xsl:value-of select="dictiography:getProperty($lang,'label.antonym')"/></xsl:if>
                            <xsl:if test="$antCount>1"><xsl:value-of select="dictiography:getProperty($lang,'label.antonyms')"/></xsl:if>:&#160;
                        </span>
                    </td>
                    <td class="lnk">
                        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryLink']">
                            <a href="#" class="lnk">
                                <xsl:value-of select="void[@property='text']/string/text()"/>
                            </a>
                            <xsl:if test="position() != last()">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>
            <xsl:if test="@property='zie'">
                <tr>
                    <td class="lnk_head">
                        <span class="lnk_head">
                            <!--<xsl:if test="$posCount>0"><xsl:value-of select="dictiography:getProperty($lang,'label.alsosee')"/></xsl:if>-->
                            <xsl:value-of select="dictiography:getProperty($lang,'label.see')"/>
                            :&#160;
                        </span>
                    </td>
                    <td class="lnk">
                        <xsl:for-each select="array/void/object[@class='com.dictiography.client.entry.EntryLink']">
                            <a href="#" class="lnk">
                                <xsl:value-of select="void[@property='text']/string/text()"/>
                            </a>
                            <xsl:if test="position() != last()">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>

            <xsl:if test="@property='afleidingen'">
                <xsl:variable name="aflCount">
                    <xsl:value-of
                            select="number(count(array/void/string))"/>
                </xsl:variable>
                <tr>
                    <td>
                        <span class="lnk_head">
                            <xsl:if test="$aflCount=1"><xsl:value-of select="dictiography:getProperty($lang,'label.derivative')"/></xsl:if>
                            <xsl:if test="$aflCount>1"><xsl:value-of select="dictiography:getProperty($lang,'label.derivatives')"/></xsl:if>:&#160;
                        </span>
                    </td>
                    <td class="afl">
                        <xsl:for-each select="array/void/string/text()">
                            <xsl:value-of select="."/>
                            <xsl:if test="position() != last()">
                                <xsl:text>, </xsl:text>
                            </xsl:if>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:if>


    </xsl:template>
</xsl:stylesheet>