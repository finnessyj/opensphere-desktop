//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.03.30 at 02:21:16 PM MDT 
//


package com.bitsys.fade.mist.state.v4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 *         Defines the structure specifying a query for a given layer, in a given
 *         region, using filters and areas.
 *       
 * 
 * <p>Java class for QueryEntryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QueryEntryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="layerId" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="areaId" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="filterId" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="includeArea" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="filterGroup" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="temp" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryEntryType")
public class QueryEntryType {

    @XmlAttribute(name = "layerId", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String layerId;
    @XmlAttribute(name = "areaId", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String areaId;
    @XmlAttribute(name = "filterId")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String filterId;
    @XmlAttribute(name = "includeArea", required = true)
    protected boolean includeArea;
    @XmlAttribute(name = "filterGroup", required = true)
    protected boolean filterGroup;
    @XmlAttribute(name = "temp")
    protected Boolean temp;

    /**
     * Gets the value of the layerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayerId() {
        return layerId;
    }

    /**
     * Sets the value of the layerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayerId(String value) {
        this.layerId = value;
    }

    public boolean isSetLayerId() {
        return (this.layerId!= null);
    }

    /**
     * Gets the value of the areaId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * Sets the value of the areaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAreaId(String value) {
        this.areaId = value;
    }

    public boolean isSetAreaId() {
        return (this.areaId!= null);
    }

    /**
     * Gets the value of the filterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilterId() {
        return filterId;
    }

    /**
     * Sets the value of the filterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilterId(String value) {
        this.filterId = value;
    }

    public boolean isSetFilterId() {
        return (this.filterId!= null);
    }

    /**
     * Gets the value of the includeArea property.
     * 
     */
    public boolean isIncludeArea() {
        return includeArea;
    }

    /**
     * Sets the value of the includeArea property.
     * 
     */
    public void setIncludeArea(boolean value) {
        this.includeArea = value;
    }

    public boolean isSetIncludeArea() {
        return true;
    }

    /**
     * Gets the value of the filterGroup property.
     * 
     */
    public boolean isFilterGroup() {
        return filterGroup;
    }

    /**
     * Sets the value of the filterGroup property.
     * 
     */
    public void setFilterGroup(boolean value) {
        this.filterGroup = value;
    }

    public boolean isSetFilterGroup() {
        return true;
    }

    /**
     * Gets the value of the temp property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isTemp() {
        return temp;
    }

    /**
     * Sets the value of the temp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTemp(boolean value) {
        this.temp = value;
    }

    public boolean isSetTemp() {
        return (this.temp!= null);
    }

    public void unsetTemp() {
        this.temp = null;
    }

}
