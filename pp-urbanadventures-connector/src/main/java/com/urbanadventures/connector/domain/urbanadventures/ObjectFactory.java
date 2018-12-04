package com.urbanadventures.connector.domain.urbanadventures;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the hello.wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ClsGetTripListRQUACountryID_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "UACountryID");
    private final static QName _ClsGetTripListRQUADestinationID_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "UADestinationID");
    private final static QName _ClsTripFullInfoCulturalRate_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "CulturalRate");
    private final static QName _ClsTripFullInfoContactPhone_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "ContactPhone");
    private final static QName _ClsTripFullInfoExclusion_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "Exclusion");
    private final static QName _ClsTripFullInfoActiveLang_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "ActiveLang");
    private final static QName _ClsTripFullInfoVoucherExchange_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "VoucherExchange");
    private final static QName _ClsTripFullInfoPhysicalRate_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "PhysicalRate");
    private final static QName _ClsTripFullInfoTipping_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "Tipping");
    private final static QName _ClsTripFullInfoCancelPolicy_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "CancelPolicy");
    private final static QName _ClsTripFullInfoStyle_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "Style");
    private final static QName _ClsTripFullInfoCarbonNeutral_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "CarbonNeutral");
    private final static QName _ClsTripFullInfoHotelLocationRequired_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "HotelLocationRequired");
    private final static QName _ClsTripFullInfoHighLights_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "HighLights");
    private final static QName _ClsTripFullInfoConfirmation_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "Confirmation");
    private final static QName _ClsTripFullInfoInclusion_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "Inclusion");
    private final static QName _ClsTripFullInfoDressStandard_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "DressStandard");
    private final static QName _ClsTripFullInfoClosure_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "Closure");
    private final static QName _ClsTripFullInfoYourTrip_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "YourTrip");
    private final static QName _ClsTripFullInfoAdditionalNote_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "AdditionalNote");
    private final static QName _ClsTripFullInfoChildPolicy_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "ChildPolicy");
    private final static QName _ClsTripBriefInfoTripStyle_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "TripStyle");
    private final static QName _ClsTravellerOtherDOB_QNAME = new QName("http://tempuri.org/urbanadventures.xsd", "DOB");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: hello.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CategoryType }
     * 
     */
    public CategoryType createCategoryType() {
        return new CategoryType();
    }

    /**
     * Create an instance of {@link ClsTripPrice }
     * 
     */
    public ClsTripPrice createClsTripPrice() {
        return new ClsTripPrice();
    }

    /**
     * Create an instance of {@link ClsBookRQ }
     * 
     */
    public ClsBookRQ createClsBookRQ() {
        return new ClsBookRQ();
    }

    /**
     * Create an instance of {@link ClsTripAlmRS }
     * 
     */
    public ClsTripAlmRS createClsTripAlmRS() {
        return new ClsTripAlmRS();
    }

    /**
     * Create an instance of {@link ClsGetUADestinationListRS }
     * 
     */
    public ClsGetUADestinationListRS createClsGetUADestinationListRS() {
        return new ClsGetUADestinationListRS();
    }

    /**
     * Create an instance of {@link PickupDropoffType }
     * 
     */
    public PickupDropoffType createPickupDropoffType() {
        return new PickupDropoffType();
    }

    /**
     * Create an instance of {@link ClsTripPhoto }
     * 
     */
    public ClsTripPhoto createClsTripPhoto() {
        return new ClsTripPhoto();
    }

    /**
     * Create an instance of {@link ClsGetStandardCountryListRS }
     * 
     */
    public ClsGetStandardCountryListRS createClsGetStandardCountryListRS() {
        return new ClsGetStandardCountryListRS();
    }

    /**
     * Create an instance of {@link ClsAccessType }
     * 
     */
    public ClsAccessType createClsAccessType() {
        return new ClsAccessType();
    }

    /**
     * Create an instance of {@link ClsTripAlmRQ }
     * 
     */
    public ClsTripAlmRQ createClsTripAlmRQ() {
        return new ClsTripAlmRQ();
    }

    /**
     * Create an instance of {@link ClsGetUACountryListRS }
     * 
     */
    public ClsGetUACountryListRS createClsGetUACountryListRS() {
        return new ClsGetUACountryListRS();
    }

    /**
     * Create an instance of {@link ClsBookingStatusRS }
     * 
     */
    public ClsBookingStatusRS createClsBookingStatusRS() {
        return new ClsBookingStatusRS();
    }

    /**
     * Create an instance of {@link ClsGetPriceRS }
     * 
     */
    public ClsGetPriceRS createClsGetPriceRS() {
        return new ClsGetPriceRS();
    }

    /**
     * Create an instance of {@link ImportBookingResponse }
     * 
     */
    public ImportBookingResponse createImportBookingResponse() {
        return new ImportBookingResponse();
    }

    /**
     * Create an instance of {@link ClsGetPriceRQ }
     * 
     */
    public ClsGetPriceRQ createClsGetPriceRQ() {
        return new ClsGetPriceRQ();
    }

    /**
     * Create an instance of {@link ClsTravellerInfo }
     * 
     */
    public ClsTravellerInfo createClsTravellerInfo() {
        return new ClsTravellerInfo();
    }

    /**
     * Create an instance of {@link ClsTripDeparture }
     * 
     */
    public ClsTripDeparture createClsTripDeparture() {
        return new ClsTripDeparture();
    }

    /**
     * Create an instance of {@link BasicInfoType }
     * 
     */
    public BasicInfoType createBasicInfoType() {
        return new BasicInfoType();
    }

    /**
     * Create an instance of {@link ClsTripAvailableDateRQ }
     * 
     */
    public ClsTripAvailableDateRQ createClsTripAvailableDateRQ() {
        return new ClsTripAvailableDateRQ();
    }

    /**
     * Create an instance of {@link ClsGetUACountryListRQ }
     * 
     */
    public ClsGetUACountryListRQ createClsGetUACountryListRQ() {
        return new ClsGetUACountryListRQ();
    }

    /**
     * Create an instance of {@link ParticipantInfoType }
     * 
     */
    public ParticipantInfoType createParticipantInfoType() {
        return new ParticipantInfoType();
    }

    /**
     * Create an instance of {@link ClsGetTripInfoRS }
     * 
     */
    public ClsGetTripInfoRS createClsGetTripInfoRS() {
        return new ClsGetTripInfoRS();
    }

    /**
     * Create an instance of {@link ClsBookingStatusRQ }
     * 
     */
    public ClsBookingStatusRQ createClsBookingStatusRQ() {
        return new ClsBookingStatusRQ();
    }

    /**
     * Create an instance of {@link TourType }
     * 
     */
    public TourType createTourType() {
        return new TourType();
    }

    /**
     * Create an instance of {@link ClsDestinationInfo }
     * 
     */
    public ClsDestinationInfo createClsDestinationInfo() {
        return new ClsDestinationInfo();
    }

    /**
     * Create an instance of {@link ImportBooking }
     * 
     */
    public ImportBooking createImportBooking() {
        return new ImportBooking();
    }

    /**
     * Create an instance of {@link ConfirmationType }
     * 
     */
    public ConfirmationType createConfirmationType() {
        return new ConfirmationType();
    }

    /**
     * Create an instance of {@link BookingType }
     * 
     */
    public BookingType createBookingType() {
        return new BookingType();
    }

    /**
     * Create an instance of {@link ClsTripAvailableDateRS }
     * 
     */
    public ClsTripAvailableDateRS createClsTripAvailableDateRS() {
        return new ClsTripAvailableDateRS();
    }

    /**
     * Create an instance of {@link ClsResultType }
     * 
     */
    public ClsResultType createClsResultType() {
        return new ClsResultType();
    }

    /**
     * Create an instance of {@link ClsTripFullInfo }
     * 
     */
    public ClsTripFullInfo createClsTripFullInfo() {
        return new ClsTripFullInfo();
    }

    /**
     * Create an instance of {@link PricingType }
     * 
     */
    public PricingType createPricingType() {
        return new PricingType();
    }

    /**
     * Create an instance of {@link ClsBookRS }
     * 
     */
    public ClsBookRS createClsBookRS() {
        return new ClsBookRS();
    }

    /**
     * Create an instance of {@link ClsTravellerOther }
     * 
     */
    public ClsTravellerOther createClsTravellerOther() {
        return new ClsTravellerOther();
    }

    /**
     * Create an instance of {@link ClsCancelBookingRQ }
     * 
     */
    public ClsCancelBookingRQ createClsCancelBookingRQ() {
        return new ClsCancelBookingRQ();
    }

    /**
     * Create an instance of {@link ClsGetTripListRQ }
     * 
     */
    public ClsGetTripListRQ createClsGetTripListRQ() {
        return new ClsGetTripListRQ();
    }

    /**
     * Create an instance of {@link ClsGetUADestinationListRQ }
     * 
     */
    public ClsGetUADestinationListRQ createClsGetUADestinationListRQ() {
        return new ClsGetUADestinationListRQ();
    }

    /**
     * Create an instance of {@link ClsTripBriefInfo }
     * 
     */
    public ClsTripBriefInfo createClsTripBriefInfo() {
        return new ClsTripBriefInfo();
    }

    /**
     * Create an instance of {@link ClsGetBookingVoucherRQ }
     * 
     */
    public ClsGetBookingVoucherRQ createClsGetBookingVoucherRQ() {
        return new ClsGetBookingVoucherRQ();
    }

    /**
     * Create an instance of {@link ClsGetTripListRS }
     * 
     */
    public ClsGetTripListRS createClsGetTripListRS() {
        return new ClsGetTripListRS();
    }

    /**
     * Create an instance of {@link ParticipantCategoryType }
     * 
     */
    public ParticipantCategoryType createParticipantCategoryType() {
        return new ParticipantCategoryType();
    }

    /**
     * Create an instance of {@link ScheduleType }
     * 
     */
    public ScheduleType createScheduleType() {
        return new ScheduleType();
    }

    /**
     * Create an instance of {@link ClsCancelBookingRS }
     * 
     */
    public ClsCancelBookingRS createClsCancelBookingRS() {
        return new ClsCancelBookingRS();
    }

    /**
     * Create an instance of {@link ClsItem }
     * 
     */
    public ClsItem createClsItem() {
        return new ClsItem();
    }

    /**
     * Create an instance of {@link ClsGetTripInfoRQ }
     * 
     */
    public ClsGetTripInfoRQ createClsGetTripInfoRQ() {
        return new ClsGetTripInfoRQ();
    }

    /**
     * Create an instance of {@link DescriptionType }
     * 
     */
    public DescriptionType createDescriptionType() {
        return new DescriptionType();
    }

    /**
     * Create an instance of {@link SupplierOperatorType }
     * 
     */
    public SupplierOperatorType createSupplierOperatorType() {
        return new SupplierOperatorType();
    }

    /**
     * Create an instance of {@link PriceType }
     * 
     */
    public PriceType createPriceType() {
        return new PriceType();
    }

    /**
     * Create an instance of {@link ClsGetBookingVoucherRS }
     * 
     */
    public ClsGetBookingVoucherRS createClsGetBookingVoucherRS() {
        return new ClsGetBookingVoucherRS();
    }

    /**
     * Create an instance of {@link ClsGetStandardCountryListRQ }
     * 
     */
    public ClsGetStandardCountryListRQ createClsGetStandardCountryListRQ() {
        return new ClsGetStandardCountryListRQ();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "UACountryID", scope = ClsGetTripListRQ.class)
    public JAXBElement<Integer> createClsGetTripListRQUACountryID(Integer value) {
        return new JAXBElement<Integer>(_ClsGetTripListRQUACountryID_QNAME, Integer.class, ClsGetTripListRQ.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "UADestinationID", scope = ClsGetTripListRQ.class)
    public JAXBElement<Integer> createClsGetTripListRQUADestinationID(Integer value) {
        return new JAXBElement<Integer>(_ClsGetTripListRQUADestinationID_QNAME, Integer.class, ClsGetTripListRQ.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "CulturalRate", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoCulturalRate(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoCulturalRate_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "ContactPhone", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoContactPhone(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoContactPhone_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "Exclusion", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoExclusion(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoExclusion_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "ActiveLang", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoActiveLang(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoActiveLang_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "VoucherExchange", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoVoucherExchange(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoVoucherExchange_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "PhysicalRate", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoPhysicalRate(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoPhysicalRate_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "Tipping", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoTipping(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoTipping_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "CancelPolicy", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoCancelPolicy(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoCancelPolicy_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "Style", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoStyle(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoStyle_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "CarbonNeutral", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoCarbonNeutral(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoCarbonNeutral_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "HotelLocationRequired", scope = ClsTripFullInfo.class)
    public JAXBElement<Boolean> createClsTripFullInfoHotelLocationRequired(Boolean value) {
        return new JAXBElement<Boolean>(_ClsTripFullInfoHotelLocationRequired_QNAME, Boolean.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "HighLights", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoHighLights(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoHighLights_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "Confirmation", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoConfirmation(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoConfirmation_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "Inclusion", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoInclusion(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoInclusion_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "DressStandard", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoDressStandard(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoDressStandard_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "Closure", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoClosure(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoClosure_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "YourTrip", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoYourTrip(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoYourTrip_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "AdditionalNote", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoAdditionalNote(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoAdditionalNote_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "ChildPolicy", scope = ClsTripFullInfo.class)
    public JAXBElement<String> createClsTripFullInfoChildPolicy(String value) {
        return new JAXBElement<String>(_ClsTripFullInfoChildPolicy_QNAME, String.class, ClsTripFullInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "TripStyle", scope = ClsTripBriefInfo.class)
    public JAXBElement<String> createClsTripBriefInfoTripStyle(String value) {
        return new JAXBElement<String>(_ClsTripBriefInfoTripStyle_QNAME, String.class, ClsTripBriefInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/urbanadventures.xsd", name = "DOB", scope = ClsTravellerOther.class)
    public JAXBElement<XMLGregorianCalendar> createClsTravellerOtherDOB(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ClsTravellerOtherDOB_QNAME, XMLGregorianCalendar.class, ClsTravellerOther.class, value);
    }

}
