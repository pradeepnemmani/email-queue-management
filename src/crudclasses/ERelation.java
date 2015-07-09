package crudclasses;
/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 10:19:20
 */
import org.neo4j.graphdb.RelationshipType;
/**
 * 
 *enum Spefies the List of posibilities 
 *all fields in a enum class should be uppercase
 *
 */
public enum ERelation implements RelationshipType 
{
	SMTP_DETAILS, EMAIL_DETAILS;

}
