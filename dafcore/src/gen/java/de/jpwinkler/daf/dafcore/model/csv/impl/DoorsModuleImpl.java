/**
 */
package de.jpwinkler.daf.dafcore.model.csv.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.jpwinkler.daf.dafcore.csv.FindObjectVisitor;
import de.jpwinkler.daf.dafcore.model.csv.AttributeDefinition;
import de.jpwinkler.daf.dafcore.model.csv.CSVPackage;
import de.jpwinkler.daf.dafcore.model.csv.DoorsModule;
import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Doors Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.jpwinkler.daf.dafcore.model.csv.impl.DoorsModuleImpl#getName <em>Name</em>}</li>
 *   <li>{@link de.jpwinkler.daf.dafcore.model.csv.impl.DoorsModuleImpl#getPath <em>Path</em>}</li>
 *   <li>{@link de.jpwinkler.daf.dafcore.model.csv.impl.DoorsModuleImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link de.jpwinkler.daf.dafcore.model.csv.impl.DoorsModuleImpl#getAttributeDefinitions <em>Attribute Definitions</em>}</li>
 *   <li>{@link de.jpwinkler.daf.dafcore.model.csv.impl.DoorsModuleImpl#getView <em>View</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DoorsModuleImpl extends DoorsTreeNodeImpl implements DoorsModule {
    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPath()
     * @generated
     * @ordered
     */
    protected static final String PATH_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPath()
     * @generated
     * @ordered
     */
    protected String path = PATH_EDEFAULT;

    /**
     * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUrl()
     * @generated
     * @ordered
     */
    protected static final String URL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUrl()
     * @generated
     * @ordered
     */
    protected String url = URL_EDEFAULT;

    /**
     * The cached value of the '{@link #getAttributeDefinitions() <em>Attribute Definitions</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getAttributeDefinitions()
     * @generated
     * @ordered
     */
    protected EList<AttributeDefinition> attributeDefinitions;

    /**
     * The default value of the '{@link #getView() <em>View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getView()
     * @generated
     * @ordered
     */
    protected static final String VIEW_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getView() <em>View</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getView()
     * @generated
     * @ordered
     */
    protected String view = VIEW_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DoorsModuleImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return CSVPackage.Literals.DOORS_MODULE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CSVPackage.DOORS_MODULE__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getPath() {
        return path;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setPath(String newPath) {
        String oldPath = path;
        path = newPath;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CSVPackage.DOORS_MODULE__PATH, oldPath, path));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void setUrl(String newUrl) {
        String oldUrl = url;
        url = newUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CSVPackage.DOORS_MODULE__URL, oldUrl, url));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EList<AttributeDefinition> getAttributeDefinitions() {
        if (attributeDefinitions == null) {
            attributeDefinitions = new EObjectContainmentEList<AttributeDefinition>(AttributeDefinition.class, this, CSVPackage.DOORS_MODULE__ATTRIBUTE_DEFINITIONS);
        }
        return attributeDefinitions;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getView() {
        return view;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setView(String newView) {
        String oldView = view;
        view = newView;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, CSVPackage.DOORS_MODULE__VIEW, oldView, view));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public AttributeDefinition findAttributeDefinition(final String name) {
        for (final AttributeDefinition ad : getAttributeDefinitions()) {
            if (ad.getName().equals(name)) {
                return ad;
            }
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public DoorsObject findObject(final String objectIdentifier) {
        final FindObjectVisitor visitor = new FindObjectVisitor(objectIdentifier);
        accept(visitor);
        return visitor.getObject();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case CSVPackage.DOORS_MODULE__ATTRIBUTE_DEFINITIONS:
                return ((InternalEList<?>)getAttributeDefinitions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case CSVPackage.DOORS_MODULE__NAME:
                return getName();
            case CSVPackage.DOORS_MODULE__PATH:
                return getPath();
            case CSVPackage.DOORS_MODULE__URL:
                return getUrl();
            case CSVPackage.DOORS_MODULE__ATTRIBUTE_DEFINITIONS:
                return getAttributeDefinitions();
            case CSVPackage.DOORS_MODULE__VIEW:
                return getView();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case CSVPackage.DOORS_MODULE__NAME:
                setName((String)newValue);
                return;
            case CSVPackage.DOORS_MODULE__PATH:
                setPath((String)newValue);
                return;
            case CSVPackage.DOORS_MODULE__URL:
                setUrl((String)newValue);
                return;
            case CSVPackage.DOORS_MODULE__ATTRIBUTE_DEFINITIONS:
                getAttributeDefinitions().clear();
                getAttributeDefinitions().addAll((Collection<? extends AttributeDefinition>)newValue);
                return;
            case CSVPackage.DOORS_MODULE__VIEW:
                setView((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case CSVPackage.DOORS_MODULE__NAME:
                setName(NAME_EDEFAULT);
                return;
            case CSVPackage.DOORS_MODULE__PATH:
                setPath(PATH_EDEFAULT);
                return;
            case CSVPackage.DOORS_MODULE__URL:
                setUrl(URL_EDEFAULT);
                return;
            case CSVPackage.DOORS_MODULE__ATTRIBUTE_DEFINITIONS:
                getAttributeDefinitions().clear();
                return;
            case CSVPackage.DOORS_MODULE__VIEW:
                setView(VIEW_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case CSVPackage.DOORS_MODULE__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case CSVPackage.DOORS_MODULE__PATH:
                return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
            case CSVPackage.DOORS_MODULE__URL:
                return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
            case CSVPackage.DOORS_MODULE__ATTRIBUTE_DEFINITIONS:
                return attributeDefinitions != null && !attributeDefinitions.isEmpty();
            case CSVPackage.DOORS_MODULE__VIEW:
                return VIEW_EDEFAULT == null ? view != null : !VIEW_EDEFAULT.equals(view);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
        switch (operationID) {
            case CSVPackage.DOORS_MODULE___FIND_ATTRIBUTE_DEFINITION__STRING:
                return findAttributeDefinition((String)arguments.get(0));
            case CSVPackage.DOORS_MODULE___FIND_OBJECT__STRING:
                return findObject((String)arguments.get(0));
        }
        return super.eInvoke(operationID, arguments);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated NOT
     */
    @Override
    public String toString() {
        if (eIsProxy()) {
            return super.toString();
        }

        final StringBuffer result = new StringBuffer();
        result.append("Doors Module (");
        result.append(name);
        result.append(')');
        return result.toString();
    }

} //DoorsModuleImpl
