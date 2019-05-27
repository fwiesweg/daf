package de.jpwinkler.daf.filter.objects;

import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.AndFilterExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.AttributeIsExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.AttributeLikeExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.AttributeMissingExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.AttributeRegexpExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.BracketFilterExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.NotFilterExpressionContext;
import de.jpwinkler.daf.filter.objects.DoorsObjectFilterParser.OrFilterExpressionContext;
import java.util.Deque;
import java.util.LinkedList;
import org.apache.commons.lang3.StringEscapeUtils;

public class DoorsObjectFilterListenerImpl extends DoorsObjectFilterBaseListener {

    private final Deque<DoorsObjectFilter> filterStack = new LinkedList<>();

    private String unescapeString(final String string) {
        return StringEscapeUtils.unescapeJava(string.substring(1, string.length() - 1));
    }

    @Override
    public void exitAndFilterExpression(final AndFilterExpressionContext ctx) {
        final DoorsObjectFilter f2 = filterStack.pop();
        final DoorsObjectFilter f1 = filterStack.pop();

        filterStack.push(new CompositeAndFilter(f1, f2));
    }

    @Override
    public void exitAttributeLikeExpression(final AttributeLikeExpressionContext ctx) {
        if (ctx.attributeName != null) {
            filterStack.push(new AttributeFilter(unescapeString(ctx.attributeName.getText()), unescapeString(ctx.attributeValue.getText()), false, false));
        } else {
            filterStack.push(new ObjectTextAndHeadingFilter(unescapeString(ctx.attributeValue.getText()), false, false));
        }
    }

    @Override
    public void exitAttributeIsExpression(final AttributeIsExpressionContext ctx) {
        if (ctx.attributeName != null) {
            filterStack.push(new AttributeFilter(unescapeString(ctx.attributeName.getText()), unescapeString(ctx.attributeValue.getText()), true, false));
        } else {
            filterStack.push(new ObjectTextAndHeadingFilter(unescapeString(ctx.attributeValue.getText()), true, false));
        }
    }

    @Override
    public void exitAttributeMissingExpression(final AttributeMissingExpressionContext ctx) {
        filterStack.push(new AttributeMissingFilter(unescapeString(ctx.attributeName.getText())));
    }

    @Override
    public void exitAttributeRegexpExpression(final AttributeRegexpExpressionContext ctx) {
        if (ctx.attributeName != null) {
            filterStack.push(new AttributeFilter(unescapeString(ctx.attributeName.getText()), unescapeString(ctx.regexp.getText()), false, true));
        } else {
            filterStack.push(new ObjectTextAndHeadingFilter(unescapeString(ctx.regexp.getText()), false, true));
        }

    }

    @Override
    public void exitBracketFilterExpression(final BracketFilterExpressionContext ctx) {
        // TODO: nothing to be done!!!!! i guess?
    }

    @Override
    public void exitNotFilterExpression(final NotFilterExpressionContext ctx) {
        filterStack.push(new NotFilter(filterStack.pop()));
    }

    @Override
    public void exitOrFilterExpression(final OrFilterExpressionContext ctx) {
        final DoorsObjectFilter f2 = filterStack.pop();
        final DoorsObjectFilter f1 = filterStack.pop();

        filterStack.push(new CompositeOrFilter(f1, f2));
    }

    public DoorsObjectFilter getFilter() {
        return filterStack.peek();
    }
}