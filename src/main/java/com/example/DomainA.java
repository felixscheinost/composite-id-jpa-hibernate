package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Sample domain class 1
 * User: fesc
 * Date: 26.10.20
 * Time: 17:52
 */
@Entity
public class DomainA {
  @Id
  @GeneratedValue
  private Long id;
}
