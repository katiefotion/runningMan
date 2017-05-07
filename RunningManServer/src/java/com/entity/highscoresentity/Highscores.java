/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.entity.highscoresentity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Flex
 */
@Entity
@Table(name = "HIGHSCORES")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Highscores.findAll", query = "SELECT h FROM Highscores h"),
  @NamedQuery(name = "Highscores.findByHid", query = "SELECT h FROM Highscores h WHERE h.hid = :hid"),
  @NamedQuery(name = "Highscores.findByPlayername", query = "SELECT h FROM Highscores h WHERE h.playername = :playername"),
  @NamedQuery(name = "Highscores.findByScore", query = "SELECT h FROM Highscores h WHERE h.score = :score")})
public class Highscores implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "HID")
  private Integer hid;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 128)
  @Column(name = "PLAYERNAME")
  private String playername;
  @Basic(optional = false)
  @NotNull
  @Column(name = "SCORE")
  private float score;

  public Highscores() {
  }

  public Highscores(Integer hid) {
    this.hid = hid;
  }

  public Highscores(Integer hid, String playername, float score) {
    this.hid = hid;
    this.playername = playername;
    this.score = score;
  }

  public Integer getHid() {
    return hid;
  }

  public void setHid(Integer hid) {
    this.hid = hid;
  }

  public String getPlayername() {
    return playername;
  }

  public void setPlayername(String playername) {
    this.playername = playername;
  }

  public float getScore() {
    return score;
  }

  public void setScore(float score) {
    this.score = score;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (hid != null ? hid.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Highscores)) {
      return false;
    }
    Highscores other = (Highscores) object;
    if ((this.hid == null && other.hid != null) || (this.hid != null && !this.hid.equals(other.hid))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.entity.highscoresentity.Highscores[ hid=" + hid + " ]";
  }
  
}
