/*
 * 13:55:59 03/04/99
 *
 * ToEntities.java
 * Copyright (C) 1999 Romain Guy
 *
 * This	free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import javax.swing.text.Element;
import org.jext.*;
import java.awt.event.ActionEvent;
import javax.swing.text.BadLocationException;

public class ToEntities extends MenuAction
{
  public ToEntities()
  {
    super("to_entities");
  }

  public void actionPerformed(ActionEvent evt)
  {
    JextTextArea textArea = getTextArea(evt);
    String selection = textArea.getSelectedText();
    if (selection != null)
      textArea.setSelectedText(doEntities(selection));
    else
    {
      try
      {
        textArea.beginCompoundEdit();
        Element map = textArea.getDocument().getDefaultRootElement();
        int count = map.getElementCount();
        for (int i = 0; i < count; i++)
        {
          Element lineElement = map.getElement(i);
          int start = lineElement.getStartOffset();
          int end = lineElement.getEndOffset() - 1;
          end -= start;
          String text = doEntities(textArea.getText(start, end));
          textArea.getDocument().remove(start, end);
          textArea.getDocument().insertString(start, text, null);
        }
        textArea.endCompoundEdit();
      }
      catch(BadLocationException bl) { }
    }
  }

  private String doEntities(String html)
  {
    StringBuffer buf = new StringBuffer();
    for(int i = 0; i < html.length(); i++)
    {
      switch(html.charAt(i))
      {
	/*case '�':
          buf.append("&eacute;");
	  break;
	case '�':
          buf.append("&egrave;");
	  break;
	case '�':
          buf.append("&ecirc;");
	  break;
	case '�':
          buf.append("&euml;");
	  break;
	case '�':
          buf.append("&agrave;");
	  break;
	case '�':
          buf.append("&acirc;");
	  break;
	case '�':
          buf.append("&auml;");
	  break;
	case '�':
          buf.append("&icirc;");
	  break;
	case '�':
          buf.append("&iuml;");
	  break;
	case '�':
          buf.append("&ugrave;");
	  break;
	case '�':
          buf.append("&uuml;");
	  break;
	case '�':
          buf.append("&ucirc;");
	  break;
	case '�':
          buf.append("&ocirc;");
	  break;
	case '�':
          buf.append("&ouml;");
	  break;
	case '�':
          buf.append("&ccedil;");
	  break;
	case '�':
          buf.append("&szlig;");
	  break;
	case '�':
          buf.append("&Auml;");
	  break;
	case '�':
          buf.append("&Ouml;");
	  break;
	case '�':
          buf.append("&Uuml;");
	  break;
	*/default:
          buf.append(html.charAt(i));
      }
    }
    return buf.toString();
  }
}

// End of ToEntities.java
