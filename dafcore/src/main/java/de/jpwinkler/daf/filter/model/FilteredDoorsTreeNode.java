/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jpwinkler.daf.filter.model;

/*-
 * #%L
 * dafcore
 * %%
 * Copyright (C) 2019 TU Berlin ASET
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import de.jpwinkler.daf.db.BackgroundTaskExecutor;
import de.jpwinkler.daf.model.DoorsFolder;
import de.jpwinkler.daf.model.DoorsModule;
import de.jpwinkler.daf.model.DoorsObject;
import de.jpwinkler.daf.model.DoorsTableRow;
import de.jpwinkler.daf.model.DoorsTreeNode;
import de.jpwinkler.daf.model.DoorsTreeNodeVisitor;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 *
 * @author fwiesweg
 */
public class FilteredDoorsTreeNode<T extends DoorsTreeNode> implements DoorsTreeNode {

    public static FilteredDoorsTreeNode<?> createFilteredTree(DoorsTreeNode node, Predicate<DoorsTreeNode> predicate, boolean recursing) {
        Predicate fullPredicate = !recursing ? predicate : predicate.or(t -> {
            boolean matched = false;
            Stack<DoorsTreeNode> tbd = new Stack<>();
            tbd.push(t);

            while (!matched && !tbd.isEmpty()) {
                DoorsTreeNode next = tbd.pop();
                if (!next.isChildrenLoaded()) {
                    continue;
                }

                matched = next.getChildren().stream()
                        .peek(tbd::add)
                        .anyMatch(predicate);
            }

            return matched;
        });

        if (node instanceof DoorsFolder) {
            return new FilteredDoorsFolder((DoorsFolder) node, fullPredicate);
        } else if (node instanceof DoorsModule) {
            return new FilteredDoorsModule((DoorsModule) node, fullPredicate);
        } else if (node instanceof DoorsTableRow) {
            return new FilteredDoorsTableRow((DoorsObject) node, fullPredicate);
        } else if (node instanceof DoorsObject) {
            return new FilteredDoorsObject((DoorsObject) node, fullPredicate);
        } else {
            throw new AssertionError();
        }
    }

    protected final T self;
    private final ForwardingChildrenList children;

    FilteredDoorsTreeNode(T self, Predicate<DoorsTreeNode> filter) {
        this.self = self;
        this.children = new ForwardingChildrenList(
                DoorsTreeNode.class, () -> self.getChildren(), filter);
    }

    public T getSelf() {
        return self;
    }

    @Override
    public List<DoorsTreeNode> getChildren() {
        return children;
    }

    @Override
    public CompletableFuture<List<DoorsTreeNode>> getChildrenAsync(BackgroundTaskExecutor executor) {
        return self.getChildrenAsync(executor)
                .thenApply(children -> this.children);
    }

    @Override
    public DoorsTreeNode getParent() {
        return self.getParent();
    }

    @Override
    public void setParent(DoorsTreeNode value) {
        self.setParent(value);
    }

    @Override
    public Map<String, String> getAttributes() {
        return self.getAttributes();
    }

    @Override
    public CompletableFuture<Map<String, String>> getAttributesAsync(BackgroundTaskExecutor executor) {
        return self.getAttributesAsync(executor);
    }

    @Override
    public String getName() {
        return self.getName();
    }

    @Override
    public void setName(String value) {
        self.setName(value);
    }

    @Override
    public String getFullName() {
        return self.getFullName();
    }

    @Override
    public List<String> getFullNameSegments() {
        return self.getFullNameSegments();
    }

    @Override
    public void accept(DoorsTreeNodeVisitor visitor) {
        self.accept(new ForwardingVisitor(visitor, children.predicate));
    }

    @Override
    public CompletableFuture<Void> acceptAsync(BackgroundTaskExecutor executor, DoorsTreeNodeVisitor visitor) {
        return self.acceptAsync(executor, new ForwardingVisitor(visitor, children.predicate));
    }

    @Override
    public boolean hasTag(String tag) {
        return self.hasTag(tag);
    }

    @Override
    public boolean hasTag(Pattern pattern) {
        return self.hasTag(pattern);
    }

    @Override
    public List<String> getTags() {
        return self.getTags();
    }

    @Override
    public void setTag(String tag) {
        self.setTag(tag);
    }

    @Override
    public void removeTag(String tag) {
        self.removeTag(tag);
    }

    @Override
    public void removeTag(Pattern pattern) {
        self.removeTag(pattern);
    }

    @Override
    public boolean canCopyFrom(DoorsTreeNode node) {
        return self.canCopyFrom(node);
    }

    @Override
    public DoorsTreeNode getChild(String name) {
        DoorsTreeNode child = self.getChild(name);
        return children.predicate.test(child) ? child : null;
    }

    @Override
    public CompletableFuture<DoorsTreeNode> getChildAsync(BackgroundTaskExecutor executor, String name) {
        return self.getChildAsync(executor, name)
                .thenApply(child -> children.predicate.test(child) ? child : null);
    }

    @Override
    public String toString() {
        return self.toString();
    }

    @Override
    public boolean equals(Object o) {
        return self.equals(o);
    }

    @Override
    public int hashCode() {
        return self.hashCode();
    }

    @Override
    public boolean isChildrenLoaded() {
        return self.isChildrenLoaded();
    }

}
