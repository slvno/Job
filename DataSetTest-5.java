/**
 * Created by chuvak on 06.12.2014.
 */
import com.example.chuvak.myapplication.CS_BindingSource;
import com.example.chuvak.myapplication.CS_DataSet;
import com.example.chuvak.myapplication.CS_DataTable;
import com.example.chuvak.myapplication.DataTablePool;
import com.example.chuvak.myapplication.Func;
import com.example.chuvak.myapplication.IDataRow;
import com.example.chuvak.myapplication.ITable;

import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;
import junit.framework.TestCase;

public class DataSetTest  extends TestCase {

    public static final boolean RESULT_OK = true;
    public DataSetTest(String pName) {
        super(pName);
    }
    public void CheckResult(boolean result) throws Exception
    {
        assertEquals(result, RESULT_OK);
    }
    /*
    public void testOK() throws Exception
    {
        boolean result = true;
        ////////////////////

        ////////////////////
        CheckResult(result);
    }
    */
    String fileName = "simple.xml";

    public void test_0_StringCompare() throws Exception
    {
        boolean result = true;
        ////////////////////
        System.out.println("StringCompare : Run");
        String compare1 = "Data";
        String compare2 = "Da";
        compare2 += "ta";

        int result_compare = compare1.compareTo(compare2);
        if (result_compare == 0)
            result = true;
        else
            result = false;

        ////////////////////
        CheckResult(result);
    }

    public void test_1_DataSetCreateAndWriteEmptyToFileXML() throws Exception
    {
        boolean result = true;
        ////////////////////
        System.out.println("testDataSetCreateAndWriteEmptyToFileXML : Run");
        CS_DataSet dataSet = new CS_DataSet(new DataTablePool());
        result = dataSet.WriteXml(fileName);
        ////////////////////
        CheckResult(result);
    }

    public void test_2_DataSetCreateAndReadEmptyXMLFile() throws Exception
    {
        boolean result = true;
        ////////////////////
        System.out.println("testDataSetCreateAndReadEmptyXMLFile : Run");
        CS_DataSet dataSet = new CS_DataSet(new DataTablePool());
        result = dataSet.ReadXml(fileName, CS_DataSet.XmlReadMode.IgnoreSchema);
        ////////////////////
        CheckResult(result);
    }

    public void test_3_DataSet() throws Exception
    {
        boolean result = true;
        ////////////////////
        System.out.println("Test 3");
        CS_DataSet dataSet = new CS_DataSet(new DataTablePool());

        //Новая строка
        CS_DataTable table = dataSet.Tables("Prot");
        IDataRow row = ((ITable)table).NewRow();

        row.setValue("ID", 0);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T12:53:54.123+03:00"));
        table.Rows().Add(row);

        //Thread.sleep(1000);

        row = ((ITable)table).NewRow();

        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T14:53:54.123+03:00"));
        table.Rows().Add(row);

        row = ((ITable)table).NewRow();

        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T15:53:54.123+03:00"));
        table.Rows().Add(row);


        result = dataSet.WriteXml(fileName);

        ////////////////////
        CheckResult(result);
    }

    public void test_4_DataSet() throws Exception
    {
        boolean result = true;
        ////////////////////
        System.out.println("Test 4");
        CS_DataSet dataSet = new CS_DataSet(new DataTablePool());

        //Новая строка
        CS_DataTable table = dataSet.Tables("Prot");

        result = dataSet.ReadXml(fileName, CS_DataSet.XmlReadMode.IgnoreSchema);

        IDataRow row = ((ITable)table).NewRow();

        if (table.Count() != 3)
            result = false;

        row.setValue("ID", 0);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T12:53:54.123+03:00"));
        if (table.Rows().get(0).EqualsRow(row) != 0)
            result = false;

        //Thread.sleep(1000);

        row = ((ITable)table).NewRow();

        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T14:53:54.123+03:00"));
        if (table.Rows().get(1).EqualsRow(row) != 0)
            result = false;

        row = ((ITable)table).NewRow();

        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T15:53:54.123+03:00"));
        if (table.Rows().get(2).EqualsRow(row) != 0)
            result = false;


        IDataRow[] selectedRows = table.Select("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T15:53:54.123+03:00"));

        if ((selectedRows == null ) || (selectedRows.length == 1))
        {
            if (table.Rows().get(2).EqualsRow(selectedRows[0]) != 0)
                result = false;
        }
        else
            result = false;

        selectedRows = table.Select("ID", 2);
        if ((selectedRows == null ) || (selectedRows.length == 2))
        {
            if ((table.Rows().get(1).EqualsRow(selectedRows[0]) != 0) ||
                (table.Rows().get(2).EqualsRow(selectedRows[1]) != 0))
                result = false;
        }

        else
            result = false;

        ////////////////////
        CheckResult(result);
    }

    public void test_5_BindingSource() throws Exception
    {
        boolean result = true;
        ////////////////////
        System.out.println("Test 5");
        CS_DataSet dataSet = new CS_DataSet(new DataTablePool());

        //Новая строка
        CS_DataTable table = dataSet.Tables("Prot");
        table.Rows().Clear();

        IDataRow row = ((ITable)table).NewRow();
        row.setValue("ID", 3);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T14:53:54.123+03:00"));
        table.Rows().Add(row);

        row = ((ITable)table).NewRow();
        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2016-12-07T15:53:54.123+03:00"));
        table.Rows().Add(row);

        row = ((ITable)table).NewRow();
        row.setValue("ID", 0);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T12:53:54.123+03:00"));
        table.Rows().Add(row);

        row = ((ITable)table).NewRow();
        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T15:53:54.123+03:00"));
        table.Rows().Add(row);

        row = ((ITable)table).NewRow();
        row.setValue("ID", 8);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2018-12-07T15:53:54.123+03:00"));
        table.Rows().Add(row);

        row = ((ITable)table).NewRow();
        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2018-12-07T15:53:54.123+03:00"));
        table.Rows().Add(row);


        result = dataSet.WriteXml(fileName);
        result = result && dataSet.ReadXml(fileName, CS_DataSet.XmlReadMode.IgnoreSchema);

        CS_BindingSource bindingSource = new CS_BindingSource();
        bindingSource.setDataSource(table);

        if (bindingSource.Count() != table.Count())
            result = false;

        bindingSource.setSort("ID, Data");

        row = ((ITable)table).NewRow();
        row.setValue("ID", 0);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T12:53:54.123+03:00"));
        if (bindingSource.Rows().get(0).EqualsRow(row) != 0)
            result = false;

        row = ((ITable)table).NewRow();
        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T15:53:54.123+03:00"));
        if (bindingSource.Rows().get(1).EqualsRow(row) != 0)
            result = false;

        row = ((ITable)table).NewRow();
        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2016-12-07T15:53:54.123+03:00"));
        if (bindingSource.Rows().get(2).EqualsRow(row) != 0)
            result = false;

        row = ((ITable)table).NewRow();
        row.setValue("ID", 2);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2018-12-07T15:53:54.123+03:00"));
        if (bindingSource.Rows().get(3).EqualsRow(row) != 0)
            result = false;

        row = ((ITable)table).NewRow();
        row.setValue("ID", 3);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T14:53:54.123+03:00"));
        if (bindingSource.Rows().get(4).EqualsRow(row) != 0)
            result = false;

        row = ((ITable)table).NewRow();
        row.setValue("ID", 8);
        row.setValue("Data", Func.DateTimeXMLStringToCalendar("2018-12-07T15:53:54.123+03:00"));
        if (bindingSource.Rows().get(5).EqualsRow(row) != 0)
            result = false;


        if (bindingSource.Find("Data", Func.DateTimeXMLStringToCalendar("2014-12-07T14:53:54.123+03:00")) != 4)
            result = false;

        bindingSource.setFilter("ID=2 AND Data=2016-12-07T15:53:54.123+03:00");

        if (bindingSource.Find("Data", Func.DateTimeXMLStringToCalendar("2016-12-07T15:53:54.123+03:00")) != 0)
            result = false;

        //------
        bindingSource.setFilter("ID=2");
        if (bindingSource.Count() != 3)
            result = false;


        int tableCount = table.Count();

        bindingSource.RemoveCurrent();

        if ((tableCount - 1) != table.Count())
            result = false;

        bindingSource.setFilter("");

        CS_DataTable table1Cil = dataSet.Tables("Cil");

        row = ((ITable)table1Cil).NewRow();
        row.setValue("ID", 0);
        row.setValue("ProtID", 8);
        table1Cil.Rows().Add(row);

        row = ((ITable)table1Cil).NewRow();
        row.setValue("ID", 1);
        row.setValue("ProtID", 8);
        table1Cil.Rows().Add(row);

        row = ((ITable)table1Cil).NewRow();
        row.setValue("ID", 2);
        row.setValue("ProtID", 8);
        table1Cil.Rows().Add(row);

        row = ((ITable)table1Cil).NewRow();
        row.setValue("ID", 3);
        row.setValue("ProtID", 8);
        table1Cil.Rows().Add(row);

        row = ((ITable)table1Cil).NewRow();
        row.setValue("ID", 4);
        row.setValue("ProtID", 2);
        table1Cil.Rows().Add(row);

        tableCount = table.Count();
        int tableCilCount = table1Cil.Count();
        int bindingSourceCount = bindingSource.Count();

        table.Rows().Remove(bindingSource.Get(bindingSource.Find("ID", 8)));

        if (((tableCount -1) !=  table.Count()) ||
                ((tableCilCount - 4) != table1Cil.Count()) ||
                (table1Cil.Rows().get(0).EqualsRow(row) != 0)
                )
            result = false;


        ////////////////////
        CheckResult(result);
    }
}
