package org.wecash.poi.treematrix;

import java.util.Set;


public interface ITreeMatrixItem<TNode, TColumn, TValue> extends Comparable<ITreeMatrixItem<TNode, TColumn, TValue>> {
    public TNode getTreeItem();

    public int getDepth();

    public String getPath();

    public String getLabel();
    
    public TValue getValueFor(TColumn key2);
    
    public Set<TColumn> getColumns();
}
