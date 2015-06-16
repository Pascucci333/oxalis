/*
 * Copyright (c) 2011,2012,2013,2015 UNIT4 Agresso AS.
 *
 * This file is part of Oxalis.
 *
 * Oxalis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Oxalis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Oxalis.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.sendregning.oxalis;


import java.io.*;

/**
 * @author steinar
 *         Date: 08.06.15
 *         Time: 21.06
 */
public class FileGenerator {

    public static final long kB = 1000L;
    public static final long MB = kB * 1000;
    public static final long GB = MB * 1000;


    public File generate(File outputFile, long requestedSize) {

        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));

            bufferedWriter.write(header);
            long numberOfItems = calculateNumberOfLines(requestedSize);
            for (int i = 0; i < numberOfItems; i++) {
                bufferedWriter.write(catalogueLine);
            }
            bufferedWriter.write(footer);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to open " + outputFile + ", reason: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write data to " + outputFile + ", reason:" + e.getMessage(), e);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new IllegalStateException("Unable to close " + outputFile);
                }
            }
        }
        return outputFile;
    }


    long calculateNumberOfLines(long requestedSize) {
        long fixedLength = header.length() + footer.length();

        long lineCount = (requestedSize - fixedLength) / catalogueLine.length();
        long remainder = (requestedSize - fixedLength) % catalogueLine.length();
        if (remainder > 0) {
            lineCount++;
        }
        return lineCount;
    }

        protected String xmlHeader =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
        protected String sbdhHeader = "<StandardBusinessDocument xmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" +
                "                          xmlns=\"http://www.unece.org/cefact/namespaces/StandardBusinessDocumentHeader\">\n" +
                "    <StandardBusinessDocumentHeader>\n" +
                "        <HeaderVersion>1.0</HeaderVersion>\n" +
                "        <Sender>\n" +
                "            <Identifier Authority=\"iso6523-actorid-upis\">0007:5567125082</Identifier>\n" +
                "        </Sender>\n" +
                "        <Receiver>\n" +
                "            <Identifier Authority=\"iso6523-actorid-upis\">0007:4455454480</Identifier>\n" +
                "        </Receiver>\n" +
                "        <DocumentIdentification>\n" +
                "            <Standard>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2</Standard>\n" +
                "            <TypeVersion>2.0</TypeVersion>\n" +
                "            <InstanceIdentifier>1070e7f0-3bae-11e3-aa6e-0800200c9a66</InstanceIdentifier>\n" +
                "            <Type>Invoice</Type>\n" +
                "            <CreationDateAndTime>2013-02-19T05:10:10</CreationDateAndTime>\n" +
                "        </DocumentIdentification>\n" +
                "        <BusinessScope>\n" +
                "            <Scope>\n" +
                "                <Type>DOCUMENTID</Type>\n" +
                "                <InstanceIdentifier>urn:oasis:names:specification:ubl:schema:xsd:Invoice-2::Invoice##urn:www.cenbii.eu:transaction:biicoretrdm010:ver1.0:#urn:www.peppol.eu:bis:peppol4a:ver1.0::2.0</InstanceIdentifier>\n" +
                "            </Scope>\n" +
                "            <Scope>\n" +
                "                <Type>PROCESSID</Type>\n" +
                "                <InstanceIdentifier>urn:www.cenbii.eu:profile:bii04:ver1.0</InstanceIdentifier>\n" +
                "            </Scope>\n" +
                "        </BusinessScope>\n" +
                "    </StandardBusinessDocumentHeader>\n";


    protected String header = xmlHeader + sbdhHeader +
            "<Catalogue xmlns:sdt=\"urn:oasis:names:specification:ubl:schema:xsd:SpecializedDatatypes-2\" xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" xmlns:a=\"urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2\" xmlns:udt=\"urn:un:unece:uncefact:data:specification:UnqualifiedDataTypesSchemaModule:2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2\" xmlns:ccts=\"urn:oasis:names:specification:ubl:schema:xsd:CoreComponentParameters-2\" xsi:schemaLocation=\"urn:oasis:names:specification:ubl:schema:xsd:Catalogue-2  ../UBL%202.1%20schema/maindoc/UBL-Catalogue-2.1.xsd\">\n" +
            "\t<cbc:UBLVersionID>2.1</cbc:UBLVersionID>\n" +
            "\t<cbc:CustomizationID>urn:www.cenbii.eu:transaction:biitrns019:ver2.0:extended:urn:www.peppol.eu:bis:peppol1a:ver2.0:extended:urn:www.difi.no:ehf:katalog:ver1.0</cbc:CustomizationID>\n" +
            "\t<cbc:ProfileID>urn:www.cenbii.eu:profile:bii01:ver2.0</cbc:ProfileID>\n" +
            "\t<cbc:ID>1234</cbc:ID>\n" +
            "\t<cbc:ActionCode listID=\"ACTIONCODE:PEPPOL\">Add</cbc:ActionCode>\n" +
            "\t<cbc:IssueDate>2013-09-23</cbc:IssueDate>\n" +
            "\t<cbc:VersionID>v1.0</cbc:VersionID>\n" +
            "\t<cac:ValidityPeriod>\n" +
            "\t\t<cbc:StartDate>2013-09-25</cbc:StartDate>\n" +
            "\t\t<cbc:EndDate>2013-12-31</cbc:EndDate>\n" +
            "\t</cac:ValidityPeriod>\n" +
            "\t<cac:ReferencedContract>\n" +
            "\t\t<cbc:ID>123123</cbc:ID>\n" +
            "\t\t<cbc:ContractType>StandardContract</cbc:ContractType>\n" +
            "\t</cac:ReferencedContract>\n" +
            "\t<cac:ProviderParty>\n" +
            "\t\t<cbc:EndpointID schemeID=\"NO:ORGNR\">1234567890</cbc:EndpointID>\n" +
            "\t\t<cac:PartyIdentification>\n" +
            "\t\t\t<cbc:ID schemeID=\"ZZZ\">Provider AS</cbc:ID>\n" +
            "\t\t</cac:PartyIdentification>\n" +
            "\t\t<cac:PartyName>\n" +
            "\t\t\t<cbc:Name>Produsentnavn</cbc:Name>\n" +
            "\t\t</cac:PartyName>\n" +
            "\t</cac:ProviderParty>\n" +
            "\t<cac:ReceiverParty>\n" +
            "\t\t<cbc:EndpointID schemeID=\"NO:ORGNR\">123456789</cbc:EndpointID>\n" +
            "\t\t<cac:PartyIdentification>\n" +
            "\t\t\t<cbc:ID schemeID=\"ZZZ\">HBE 1018275</cbc:ID>\n" +
            "\t\t</cac:PartyIdentification>\n" +
            "\t\t<cac:PartyName>\n" +
            "\t\t\t<cbc:Name>Helse Vest</cbc:Name>\n" +
            "\t\t</cac:PartyName>\n" +
            "\t</cac:ReceiverParty>\n" +
            "\t<cac:SellerSupplierParty>\n" +
            "\t\t<cac:Party>\n" +
            "\t\t\t<cbc:EndpointID schemeID=\"NO:ORGNR\">987654321</cbc:EndpointID>\n" +
            "\t\t\t<cac:PartyIdentification>\n" +
            "\t\t\t\t<cbc:ID schemeID=\"NO:ORGNR\">984297793</cbc:ID>\n" +
            "\t\t\t</cac:PartyIdentification>\n" +
            "\t\t\t<cac:PartyName>\n" +
            "\t\t\t\t<cbc:Name>Supplier</cbc:Name>\n" +
            "\t\t\t</cac:PartyName>\n" +
            "\t\t\t<cac:PostalAddress>\n" +
            "\t\t\t\t<cbc:StreetName>Per Krohgs vei 1,Karihaugen</cbc:StreetName>\n" +
            "\t\t\t\t<cbc:CityName>OSLO</cbc:CityName>\n" +
            "\t\t\t\t<cbc:CountrySubentity>Norway</cbc:CountrySubentity>\n" +
            "\t\t\t\t<cac:Country>\n" +
            "\t\t\t\t\t<cbc:IdentificationCode listID=\"ISO3166-1\">NO</cbc:IdentificationCode>\n" +
            "\t\t\t\t</cac:Country>\n" +
            "\t\t\t</cac:PostalAddress>\n" +
            "\t\t\t<cac:Contact>\n" +
            "\t\t\t\t<cbc:Name>Ole Olsen</cbc:Name>\n" +
            "\t\t\t\t<cbc:Telephone>+46123123123</cbc:Telephone>\n" +
            "\t\t\t\t<cbc:ElectronicMail>test@ibxeurope.com</cbc:ElectronicMail>\n" +
            "\t\t\t</cac:Contact>\n" +
            "\t\t</cac:Party>\n" +
            "\t</cac:SellerSupplierParty>\n" +
            "\t<!-- Catalogue items starts here...-->\n";

    protected String catalogueLine = "\t<cac:CatalogueLine>\n" +
            "\t\t<cbc:ID>200</cbc:ID>\n" +
            "\t\t<cbc:ActionCode listID=\"ACTIONCODE:BII\">Add</cbc:ActionCode>\n" +
            "\t\t<cbc:OrderableIndicator>true</cbc:OrderableIndicator>\n" +
            "\t\t<cbc:OrderableUnit>EA</cbc:OrderableUnit>\n" +
            "\t\t<cbc:ContentUnitQuantity unitCode=\"EA\" unitCodeListID=\"UNECERec20\">1</cbc:ContentUnitQuantity>\n" +
            "\t\t<cbc:OrderQuantityIncrementNumeric>1</cbc:OrderQuantityIncrementNumeric>\n" +
            "\t\t<cbc:MinimumOrderQuantity unitCode=\"EA\" unitCodeListID=\"UNECERec20\">1</cbc:MinimumOrderQuantity>\n" +
            "\t\t<cbc:MaximumOrderQuantity unitCode=\"EA\" unitCodeListID=\"UNECERec20\">1</cbc:MaximumOrderQuantity>\n" +
            "\t\t<cbc:PackLevelCode listID=\"GS17009:PEPPOL\">CU</cbc:PackLevelCode>\n" +
            "\t\t<cac:LineValidityPeriod>\n" +
            "\t\t\t<cbc:StartDate>2013-10-01</cbc:StartDate>\n" +
            "\t\t\t<cbc:EndDate>2013-12-31</cbc:EndDate>\n" +
            "\t\t</cac:LineValidityPeriod>\n" +
            "\t\t<cac:RequiredItemLocationQuantity>\n" +
            "\t\t\t<cbc:LeadTimeMeasure unitCode=\"DAY\">13</cbc:LeadTimeMeasure>\n" +
            "\t\t\t<cac:Price>\n" +
            "\t\t\t\t<cbc:PriceAmount currencyID=\"NOK\">504.5</cbc:PriceAmount>\n" +
            "\t\t\t</cac:Price>\n" +
            "\t\t</cac:RequiredItemLocationQuantity>\n" +
            "\t\t<cac:Item>\n" +
            "\t\t\t<cbc:Description/>\n" +
            "\t\t\t<cbc:PackQuantity unitCode=\"EA\" unitCodeListID=\"UNECERec20\">1</cbc:PackQuantity>\n" +
            "\t\t\t<cbc:Name>LÅSKASSE LK1362/28 R STOLPE25</cbc:Name>\n" +
            "\t\t\t<cac:SellersItemIdentification>\n" +
            "\t\t\t\t<cbc:ID>2451015</cbc:ID>\n" +
            "\t\t\t</cac:SellersItemIdentification>\n" +
            "\t\t\t<cac:ManufacturersItemIdentification>\n" +
            "\t\t\t\t<cbc:ID>2451015</cbc:ID>\n" +
            "\t\t\t</cac:ManufacturersItemIdentification>\n" +
            "\t\t\t<cac:StandardItemIdentification>\n" +
            "\t\t\t\t<cbc:ID schemeID=\"GTIN\">05704368876486</cbc:ID>\n" +
            "\t\t\t</cac:StandardItemIdentification>\n" +
            "\t\t\t<cac:CommodityClassification>\n" +
            "\t\t\t\t<cbc:ItemClassificationCode listID=\"UNSPSC\">46171500</cbc:ItemClassificationCode>\n" +
            "\t\t\t</cac:CommodityClassification>\n" +
            "\t\t\t<cac:ClassifiedTaxCategory>\n" +
            "\t\t\t\t<cbc:ID schemeID=\"UNCL5305\">S</cbc:ID>\n" +
            "\t\t\t\t<cac:TaxScheme>\n" +
            "\t\t\t\t\t<cbc:ID>VAT</cbc:ID>\n" +
            "\t\t\t\t</cac:TaxScheme>\n" +
            "\t\t\t</cac:ClassifiedTaxCategory>\n" +
            "\t\t\t<cac:ManufacturerParty>\n" +
            "\t\t\t\t<cac:PartyName>\n" +
            "\t\t\t\t\t<cbc:Name>Manufacturer</cbc:Name>\n" +
            "\t\t\t\t</cac:PartyName>\n" +
            "\t\t\t</cac:ManufacturerParty>\n" +
            "\t\t\t<cac:Dimension>\n" +
            "\t\t\t\t<cbc:AttributeID schemeID=\"UNCL6313\">AAE</cbc:AttributeID>\n" +
            "\t\t\t\t<cbc:Measure unitCode=\"KGM\">0.574</cbc:Measure>\n" +
            "\t\t\t</cac:Dimension>\n" +
            "\t\t</cac:Item>\n" +
            "\t</cac:CatalogueLine>\n";

    String footer = "</Catalogue>\n" +
            "</StandardBusinessDocument>\n";
}
