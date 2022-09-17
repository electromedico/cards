/* (C) 2022 */
package com.example.demo.utils;

import com.example.demo.errors.RevisionsDontMatchException;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Revision {

  private int value;

  public void matchAndBump(Revision revision) throws RevisionsDontMatchException {
    match(revision);
    this.setValue(++value);
  }

  public void match(Revision revision) throws RevisionsDontMatchException {
    if (this.value != revision.value)
      throw new RevisionsDontMatchException(revision.value, this.value);
  }

  public String toString() {
    return String.valueOf(this.getValue());
  }
}
