package org.wecash.model.matching.date;

import java.util.Date;

import org.wecash.model.matching.IExpenseMatcher;

public interface IDateMatcher extends IExpenseMatcher{
    public Date getFrom();
    public Date getTo();
}
