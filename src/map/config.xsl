<?xml version="1.0"  encoding="ISO-8859-1"?>
 
<xsl:transform
	version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output
		omit-xml-declaration="yes"
		method="html"
		version="1.0"
		encoding="ISO-8859-1"
		doctype-public="-//W3C//DTD Xhtml 1.0 Transitional//EN"
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"
		indent="yes"
		media-type="text/html"
		/>

	<xsl:template match="/">
		<html>
			<head>
				<title>Configuration view</title>
				<link REL="SHORTCUT ICON" HREF="jqapi.ico" />
				<link type="text/css" href="../../WebContent/css/config.css" rel="stylesheet" />
				<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.min.js"  ></script>
			</head>
			<body>
			    
				<xsl:apply-templates select="root" />
			</body>
		</html>		
	</xsl:template>

	<xsl:template  match="screen" >
		<div> HTML Templates: <xsl:value-of select="htmltempalte"></xsl:value-of></div>
		<div> Included jsp: <xsl:value-of select="includedjsp"></xsl:value-of></div>
		<div> Optional Callback: <xsl:value-of select="callbackclass"></xsl:value-of></div>
		
		<xsl:apply-templates select="scripts"/>
		<xsl:apply-templates select="stylesheets"/>
	</xsl:template>
	<xsl:template  match="scripts" >
		Scripts:
		 <xsl:for-each select="child::node()">
		   <xsl:choose>
		    
		     <xsl:when test="name()='scriptinclude'">
		     <xsl:variable name="mytxt"><xsl:value-of select="self::text()"></xsl:value-of> </xsl:variable>
		     <xsl:variable name="myname"><xsl:value-of select="name()"></xsl:value-of> </xsl:variable>
		       Included Scripts: <xsl:value-of select="text()"></xsl:value-of><br/>
		      </xsl:when>
		      <xsl:when test="self::text()">
		       Inline Script:<pre> <xsl:value-of select="self::text()"></xsl:value-of></pre>
		      </xsl:when>
		     <xsl:otherwise></xsl:otherwise>
		   </xsl:choose>
		 </xsl:for-each>
		
	</xsl:template> 
	<xsl:template  match="stylesheets" >
		Stylesheets:
		 <xsl:for-each select="child::node()">
		   <xsl:choose>
		     <xsl:when test="name()='styleinclude'">
		       Included Stylesheets: <xsl:value-of select="text()"></xsl:value-of><br/>
		      </xsl:when>
		      <xsl:when test="self::text()">
		       Inline Style Elements:<pre> <xsl:value-of select="self::text()"></xsl:value-of></pre>
		      </xsl:when>
		     <xsl:otherwise></xsl:otherwise>
		   </xsl:choose>
		 </xsl:for-each>
		
	</xsl:template> 
	
	<xsl:template  match="panels/panel" >
		<div class="panel">
		 <h2>Panel Id: <span > <xsl:value-of select="@id" /></span></h2>
		 SQLs:  JS RPC:
		  <xsl:for-each select="crud/jsonrpc">
		     <div class="query"> <xsl:value-of select="." /> </div><br/>
		   </xsl:for-each>
				On Load Select: <xsl:for-each select="crud/selectonload">
				  <div class="query"> <xsl:value-of select="." /><br/><br/>
				<b>Count for Pagination:</b> <div class="query">  <xsl:value-of select="countquery" /></div><br/>
				</div>
				  </xsl:for-each>
				Details Select:  <div class="query"> <xsl:value-of select="crud/sqlselect" /></div><br/>
				Insert Query: <div class="query">  <xsl:value-of select="crud/sqlinsert" /></div><br/>
				Delete Query: <div class="query">  <xsl:value-of select="crud/sqldelete" /></div><br/>
				SQL Update:  <div class="query"> <xsl:value-of select="crud/sqlupdate" /></div><br/>
		
		<xsl:apply-templates select="fields/field"/> <br/>
		 <xsl:for-each select="button">
		 	Button: ID: <xsl:value-of select="@forid" />, Replace:<xsl:value-of select="@replace" /><br/>
		 </xsl:for-each>
		</div>
	</xsl:template> 
	<xsl:template  match="fields/field" >
	 <div class="field">
		 <xsl:for-each select="child::node()">
		      <xsl:choose>
			      <xsl:when test="name()!= ''">
			       Field:<xsl:value-of select="@forid"/>, Column: <xsl:value-of select="@column"/>, Database Type: <xsl:value-of select="@dbdatatype"/>,Replace:<xsl:value-of select="@replace" />, HTML: &lt; <xsl:value-of select="name()" /> &gt;<br/>
		  			<pre> Inline Element HTML:<xsl:value-of select="text"/></pre>
			      </xsl:when> 
			      
		      <xsl:otherwise></xsl:otherwise>
		   </xsl:choose>
		 </xsl:for-each>
		</div>
	</xsl:template>
</xsl:transform>		