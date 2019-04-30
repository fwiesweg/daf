/**
 */
package de.jpwinkler.daf.doorsdb.model;

import java.util.Date;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DB Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.jpwinkler.daf.doorsdb.model.DBVersion#getModule <em>Module</em>}</li>
 *   <li>{@link de.jpwinkler.daf.doorsdb.model.DBVersion#getDate <em>Date</em>}</li>
 *   <li>{@link de.jpwinkler.daf.doorsdb.model.DBVersion#getAttributes <em>Attributes</em>}</li>
 * </ul>
 *
 * @see de.jpwinkler.daf.doorsdb.model.DoorsDBPackage#getDBVersion()
 * @model
 * @generated
 */
public interface DBVersion extends EObject {
	/**
	 * Returns the value of the '<em><b>Module</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.jpwinkler.daf.doorsdb.model.DBModule#getVersions <em>Versions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module</em>' container reference.
	 * @see #setModule(DBModule)
	 * @see de.jpwinkler.daf.doorsdb.model.DoorsDBPackage#getDBVersion_Module()
	 * @see de.jpwinkler.daf.doorsdb.model.DBModule#getVersions
	 * @model opposite="versions" transient="false"
	 * @generated
	 */
	DBModule getModule();

	/**
	 * Sets the value of the '{@link de.jpwinkler.daf.doorsdb.model.DBVersion#getModule <em>Module</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module</em>' container reference.
	 * @see #getModule()
	 * @generated
	 */
	void setModule(DBModule value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(Date)
	 * @see de.jpwinkler.daf.doorsdb.model.DoorsDBPackage#getDBVersion_Date()
	 * @model
	 * @generated
	 */
	Date getDate();

	/**
	 * Sets the value of the '{@link de.jpwinkler.daf.doorsdb.model.DBVersion#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(Date value);

	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attributes</em>' map.
	 * @see de.jpwinkler.daf.doorsdb.model.DoorsDBPackage#getDBVersion_Attributes()
	 * @model mapType="de.jpwinkler.daf.doorsdb.model.StringToStringMap&lt;org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString&gt;"
	 * @generated
	 */
	EMap<String, String> getAttributes();

} // DBVersion