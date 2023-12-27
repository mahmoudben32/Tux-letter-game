<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:tux="http://myGame/tux">
    <xsl:output method="html"/>

    <!-- Template pour l'élément racine -->
    <xsl:template match="/">
        <xhtml>
            <head>
                <title>Dictionnaire trié selon l'alphabet</title>
            </head>
            <body>
                <h1>Dictionnaire</h1>
                <xsl:apply-templates select="//tux:mot">   
                    <xsl:sort select="@niveau" order="ascending"/>    
                    <xsl:sort select="."/>                
                </xsl:apply-templates>
            </body>
        </xhtml>
    </xsl:template>
    
    <!-- Template pour chaque élément "mot" -->
    <xsl:template match="tux:mot">
        <p>- <xsl:value-of select="."/> (niveau <xsl:value-of select="@niveau"/>)</p>
    </xsl:template>
        
</xsl:stylesheet>
