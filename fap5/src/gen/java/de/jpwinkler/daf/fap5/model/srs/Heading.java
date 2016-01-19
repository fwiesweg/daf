/**
 */
package de.jpwinkler.daf.fap5.model.srs;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Heading</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link de.jpwinkler.daf.fap5.model.srs.Heading#getName <em>Name</em>}</li>
 * </ul>
 *
 * @see de.jpwinkler.daf.fap5.model.srs.SrsPackage#getHeading()
 * @model
 * @generated
 */
public interface Heading extends FunctionalSpecificationContainer {

	/**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see de.jpwinkler.daf.fap5.model.srs.SrsPackage#getHeading_Name()
     * @model
     * @generated
     */
	String getName();

	/**
     * Sets the value of the '{@link de.jpwinkler.daf.fap5.model.srs.Heading#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
	void setName(String value);
} // Heading
