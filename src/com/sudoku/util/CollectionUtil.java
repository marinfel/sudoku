/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sudoku.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author JE
 */
public class CollectionUtil {
  public static <T> ArrayList<T> inter(Collection<T> l1, Collection<T> l2) {
    ArrayList<T> l = new ArrayList<>();
    for (T e : l1) {
      if (l2.contains(e)) {
        l.add(e);
      }
    }
    return l;
  }

  public static <T> ArrayList<T> union(Collection<T> l1, Collection<T> l2) {
    Set<T> s = new HashSet<>();
    s.addAll(l1);
    s.addAll(l2);
    return new ArrayList<>(s);
  }

  public static <T> ArrayList<T> merge(Collection<T> l1, Collection<T> l2,
                                       T toRemove) {
    ArrayList<T> al = union(l1, l2);
    al.remove(toRemove);
    return al;
  }
}
