package com.example;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Sample domain class having a composite ID
 * User: fesc
 * Date: 26.10.20
 * Time: 17:51
 */
@Entity
public class CompositeIdDomain implements Serializable {
  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  private DomainA domainA;

  @Id
  @ManyToOne(fetch = FetchType.LAZY)
  private DomainB domainB;

  @Column
  private String otherProperty;

  public CompositeIdDomain(DomainA domainA, DomainB domainB, String otherProperty) {
    this.domainA = domainA;
    this.domainB = domainB;
    this.otherProperty = otherProperty;
  }

  public CompositeIdDomain() {
  }
}
