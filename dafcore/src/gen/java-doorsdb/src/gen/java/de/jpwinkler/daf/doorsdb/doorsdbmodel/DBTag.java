/**
 */
package de.jpwinkler.daf.doorsdb.doorsdbmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DB Tag</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.jpwinkler.daf.doorsdb.doorsdbmodel.DBTag#getName <em>Name</em>}</li>
 *   <li>{@link de.jpwinkler.daf.doorsdb.doorsdbmodel.DBTag#getModules <em>Modules</em>}</li>
 * </ul>
 *
 * @see de.jpwinkler.daf.doorsdb.doorsdbmodel.DoorsDBPackage#getDBTag()
 * @model
 * @generated
 */
public interface DBTag extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see de.jpwinkler.daf.doorsdb.doorsdbmodel.DoorsDBPackage#getDBTag_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link de.jpwinkler.daf.doorsdb.doorsdbmodel.DBTag#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Modules</b></em>' reference list.
	 * The list contents are of type {@link de.jpwinkler.daf.doorsdb.doorsdbmodel.DBModule}.
	 * It is bidirectional and its opposite is '{@link de.jpwinkler.daf.doorsdb.doorsdbmodel.DBModule#getTags <em>Tags</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modules</em>' reference list.
	 * @see de.jpwinkler.daf.doorsdb.doorsdbmodel.DoorsDBPackage#getDBTag_Modules()
	 * @see de.jpwinkler.daf.doorsdb.doorsdbmodel.DBModule#getTags
	 * @model opposite="tags"
	 * @generated
	 */
	EList<DBModule> getModules();

} // DBTag
