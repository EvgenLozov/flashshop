package com.flashshop.executor;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Evgen
 * Date: 10.01.13
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public interface Task {
    public void execute() throws IOException, SAXException, SQLException, ParserConfigurationException;
}
