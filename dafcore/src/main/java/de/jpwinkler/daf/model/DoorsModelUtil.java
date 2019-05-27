package de.jpwinkler.daf.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DoorsModelUtil {

    public static DoorsObject getPreviousObject(final DoorsObject o) {
        if (o.getParent() != null && o.getParent().getChildren().indexOf(o) > 0) {
            return (DoorsObject) o.getParent().getChildren().get(o.getParent().getChildren().indexOf(o) - 1);
        } else {
            return null;
        }
    }

    public static DoorsObject getNextObject(final DoorsObject o) {
        if (o.getParent() != null && o.getParent().getChildren().indexOf(o) < o.getParent().getChildren().size() - 1) {
            return (DoorsObject) o.getParent().getChildren().get(o.getParent().getChildren().indexOf(o) + 1);
        } else {
            return null;
        }
    }

    public static void ensureAttributeDefinition(final DoorsModule module, final String attributeName) {
        List<String> attrs = module.getObjectAttributes();
        if (!attrs.contains(attributeName)) {
            attrs = new ArrayList<>(attrs);
            attrs.add(attributeName);
            module.setObjectAttributes(attrs);
        }
    }

    public static int countObjects(final DoorsTreeNode module) {
        final int childCount = module.getChildren().stream().mapToInt(n -> countObjects(n)).sum();
        if (module instanceof DoorsModule) {
            return childCount;
        } else {
            return 1 + childCount;
        }

    }

    public static Date parseDate(final String doorsDateString) throws IOException {
        try {
            return new SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH).parse(doorsDateString);
        } catch (ParseException ex) {
            throw new IOException("Failed parsing date in module", ex);
        }
    }

    public static void compareModules(final DoorsModule left, final DoorsModule right) {
        final Map<String, DoorsObject> leftObjects = new HashMap<>();
        final Map<String, DoorsObject> rightObjects = new HashMap<>();

        left.accept(new DoorsTreeNodeVisitor<>(DoorsObject.class) {
            @Override
            public boolean visitPreTraverse(final DoorsObject object) {
                leftObjects.put(object.getObjectIdentifier(), object);
                return true;
            }
        });

        right.accept(new DoorsTreeNodeVisitor<>(DoorsObject.class) {
            @Override
            public boolean visitPreTraverse(final DoorsObject object) {
                rightObjects.put(object.getObjectIdentifier(), object);
                return true;
            }
        });

        // search for added and changed objects
        for (final Entry<String, DoorsObject> e : rightObjects.entrySet()) {
            if (!leftObjects.containsKey(e.getKey())) {
                System.out.println("added: " + e.getValue());
            } else {
                final DoorsObject old = leftObjects.get(e.getKey());
                if (!old.getText().equals(e.getValue().getText())) {
                    System.out.println("changed: " + e.getValue());
                }
            }
        }

        // search for deleted objects
        for (final Entry<String, DoorsObject> e : leftObjects.entrySet()) {
            if (!rightObjects.containsKey(e.getKey())) {
                System.out.println("added: " + e.getValue());
            }
        }
    }

    public static String getTemplateName(final DoorsModule module) {
        String moduleType = "";
        final String templateShortName = module.getAttributes().get("IN_TemplateShortName");
        if ("STLH".equals(templateShortName)) {
            moduleType = "stlh";
        } else if ("SB".equals(templateShortName)) {
            moduleType = "slh";
        } else {
            return null;
        }

        final String templateVersion = module.getAttributes().get("Template Version");
        if (templateVersion != null) {
            return moduleType + "-" + templateVersion;
        } else {
            return null;
        }

    }

    public static DoorsFolder createFolder(DoorsTreeNode parent, String name) {
        return create(parent, DoorsFactory.eINSTANCE.createDoorsFolder(), name);
    }

    public static DoorsModule createModule(DoorsTreeNode parent, String name) {
        DoorsModule module = create(parent, DoorsFactory.eINSTANCE.createDoorsModule(), name);
        module.setObjectAttributes(DoorsSystemAttributes.valuesFor(DoorsObject.class)
                .filter(v -> !v.isSystemKey())
                .map(a -> a.getKey())
                .collect(Collectors.toList()));
        return module;
    }

    public static DoorsObject createObject(DoorsTreeNode parent, String objectText) {
        DoorsObject object = create(parent, DoorsFactory.eINSTANCE.createDoorsObject(), null);
        if (parent instanceof DoorsModule) {
            object.setObjectLevel(1);
        } else if (parent instanceof DoorsObject) {
            object.setObjectLevel(((DoorsObject) parent).getObjectLevel() + 1);
        } else if (parent instanceof DoorsFolder) {
            throw new IllegalArgumentException("parent");
        }

        object.setObjectText(objectText);
        object.setObjectHeading("");
        return object;
    }

    private static <T extends DoorsTreeNode> T create(DoorsTreeNode parent, T object, String name) {
        object.setName(name);
        object.setParent(parent);
        return object;
    }

    public static <T extends DoorsTreeNode> T createCopy(T source, DoorsTreeNode newParent) {
        T copy;
        if (source instanceof DoorsObject) {
            copy = (T) DoorsFactory.eINSTANCE.createDoorsObject();
        } else if (source instanceof DoorsModule) {
            copy = (T) DoorsFactory.eINSTANCE.createDoorsModule();
        } else if (source instanceof DoorsFolder) {
            copy = (T) DoorsFactory.eINSTANCE.createDoorsFolder();
        } else {
            throw new AssertionError();
        }

        return (T) copy.copyFrom(source, newParent);
    }

    // Defined here to prevent forward references in DoorsSystemAttributes
    static final Function<String, List> LIST_PARSER = s -> (s == null || s.isEmpty()) ? Collections.emptyList() : Arrays.asList(s.split(","));
    static final Function<List, String> LIST_WRITER = l -> (l == null) ? null : (String) l.stream()
            .filter(s1 -> s1 != null).map(s1 -> s1.toString()).reduce((s1, s2) -> s1 + "," + s2).orElse(null);

    static final Function<String, Integer> INT_PARSER = s -> (s == null || s.isEmpty()) ? 0 : Integer.parseInt(s);
    static final Function<Integer, String> INT_WRITER = i -> (i == null) ? null : Integer.toString(i);

    static final Function<String, String> IDENTITY = s -> s;
}