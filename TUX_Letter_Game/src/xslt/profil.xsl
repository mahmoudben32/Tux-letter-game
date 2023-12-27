<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:tux="http://myGame/tux">

    <xsl:output method="html" encoding="UTF-8"/>

    <!-- Template pour l'élément racine -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Profil du Joueur</title>
                <link rel="stylesheet" href="style.css"/>
            </head>
            <body>
                <h1>Profil du Joueur</h1>
                <div>
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="tux:profil/tux:avatar"/>
                        </xsl:attribute>
                        <xsl:attribute name="alt">Avatar</xsl:attribute>
                    </img>
                    <p>Nom: <xsl:value-of select="tux:profil/tux:nom"/></p>
                    <p>Anniversaire: <xsl:value-of select="tux:profil/tux:anniversaire"/></p>
                </div>
                <h2>Parties</h2>
                <table>
                    <tr>
                        <th>Date</th>
                        <th>Temps</th>
                        <th>Mot</th>
                        <th>Trouvé</th>
                    </tr>
                    <xsl:apply-templates select="//tux:partie"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <!-- Template pour chaque élément "partie" -->
    <xsl:template match="tux:partie">
        <tr>
            <td>
                <xsl:value-of select="@date"/>
            </td>
            <td>
                <xsl:value-of select="tux:temps"/>
            </td>
            <td>
                <xsl:value-of select="tux:mot"/>
            </td>
            <td>
                <xsl:value-of select="@trouvé"/>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>
