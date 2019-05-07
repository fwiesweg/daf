/**
 */
package de.jpwinkler.daf.model.impl;

import de.jpwinkler.daf.csv.FindObjectVisitor;
import de.jpwinkler.daf.model.DoorsModuleVersion;
import de.jpwinkler.daf.model.DoorsModule;
import de.jpwinkler.daf.model.DoorsObject;
import de.jpwinkler.daf.model.DoorsPackage;
import de.jpwinkler.daf.model.DoorsSystemAttributes;
import de.jpwinkler.daf.model.DoorsTreeNode;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Doors Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link de.jpwinkler.daf.model.impl.DoorsModuleImpl#getPath <em>Path</em>}</li>
 *   <li>{@link de.jpwinkler.daf.model.impl.DoorsModuleImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link de.jpwinkler.daf.model.impl.DoorsModuleImpl#getView <em>View</em>}</li>
 *   <li>{@link de.jpwinkler.daf.model.impl.DoorsModuleImpl#getVersions <em>Versions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DoorsModuleImpl extends DoorsTreeNodeImpl implements DoorsModule {

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
	 * The cached value of the '{@link #getVersions() <em>Versions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersions()
	 * @generated
	 * @ordered
	 */
    protected EList<DoorsModuleVersion> versions;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    protected DoorsModuleImpl() {
		super();
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    protected EClass eStaticClass() {
		return DoorsPackage.Literals.DOORS_MODULE;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public String getPath() {
		return path;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DoorsPackage.DOORS_MODULE__PATH, oldPath, path));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public String getUrl() {
		return url;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setUrl(String newUrl) {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DoorsPackage.DOORS_MODULE__URL, oldUrl, url));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public String getView() {
		return view;
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void setView(String newView) {
		String oldView = view;
		view = newView;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DoorsPackage.DOORS_MODULE__VIEW, oldView, view));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public List<DoorsModuleVersion> getVersions() {
		if (versions == null) {
			versions = new EObjectContainmentWithInverseEList<DoorsModuleVersion>(DoorsModuleVersion.class, this, DoorsPackage.DOORS_MODULE__VERSIONS, DoorsPackage.DOORS_MODULE_VERSION__MODULE);
		}
		return versions;
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
	 * @generated NOT
	 */
	@Override
	public List<String> getObjectAttributes() {
            return DoorsSystemAttributes.OBJECT_ATTRIBUTES.getValue(List.class, attributes);
	}

				/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setObjectAttributes(Collection attrs) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

				/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated NOT
     */
    @Override
    public DoorsModuleVersion getLatestVersion() {
        DoorsModuleVersion latest = null;
        for (final DoorsModuleVersion version : getVersions()) {
            if (latest == null || latest.getDate().before(version.getDate())) {
                latest = version;
            }
        }
        return latest;
    }
    
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated NOT
     */
    @Override
    public DoorsObject copyFrom(DoorsTreeNode newModule) {
        if (!super.canCopyFrom(newModule)) {
            throw new IllegalArgumentException();
        }

        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DoorsPackage.DOORS_MODULE__VERSIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getVersions()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DoorsPackage.DOORS_MODULE__VERSIONS:
				return ((InternalEList<?>)getVersions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DoorsPackage.DOORS_MODULE__PATH:
				return getPath();
			case DoorsPackage.DOORS_MODULE__URL:
				return getUrl();
			case DoorsPackage.DOORS_MODULE__VIEW:
				return getView();
			case DoorsPackage.DOORS_MODULE__VERSIONS:
				return getVersions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DoorsPackage.DOORS_MODULE__PATH:
				setPath((String)newValue);
				return;
			case DoorsPackage.DOORS_MODULE__URL:
				setUrl((String)newValue);
				return;
			case DoorsPackage.DOORS_MODULE__VIEW:
				setView((String)newValue);
				return;
			case DoorsPackage.DOORS_MODULE__VERSIONS:
				getVersions().clear();
				getVersions().addAll((Collection<? extends DoorsModuleVersion>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public void eUnset(int featureID) {
		switch (featureID) {
			case DoorsPackage.DOORS_MODULE__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case DoorsPackage.DOORS_MODULE__URL:
				setUrl(URL_EDEFAULT);
				return;
			case DoorsPackage.DOORS_MODULE__VIEW:
				setView(VIEW_EDEFAULT);
				return;
			case DoorsPackage.DOORS_MODULE__VERSIONS:
				getVersions().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DoorsPackage.DOORS_MODULE__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case DoorsPackage.DOORS_MODULE__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case DoorsPackage.DOORS_MODULE__VIEW:
				return VIEW_EDEFAULT == null ? view != null : !VIEW_EDEFAULT.equals(view);
			case DoorsPackage.DOORS_MODULE__VERSIONS:
				return versions != null && !versions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc --> @generated
     */
    @Override
				@SuppressWarnings("unchecked")
    public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case DoorsPackage.DOORS_MODULE___FIND_OBJECT__STRING:
				return findObject((String)arguments.get(0));
			case DoorsPackage.DOORS_MODULE___GET_OBJECT_ATTRIBUTES:
				return getObjectAttributes();
			case DoorsPackage.DOORS_MODULE___SET_OBJECT_ATTRIBUTES__COLLECTION:
				setObjectAttributes((Collection)arguments.get(0));
				return null;
			case DoorsPackage.DOORS_MODULE___GET_LATEST_VERSION:
				return getLatestVersion();
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
