import com.micmiu.bigdata.deserteagle.hbase.client.SimpleHbaseConnectionManager;
import com.micmiu.bigdata.deserteagle.hbase.service.HbaseServiceImpl;
import org.apache.hadoop.hbase.client.HTableInterface;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/8/2016
 * Time: 14:05
 */
public class HbaseServiceTest {

	public static void main(String[] args) {

		String quorum = "192.168.0.30,192.168.0.31,192.168.0.32";
		int port = 2181;
		String znode = "/hyperbase1";
		SimpleHbaseConnectionManager hbaseConnectionManager = new SimpleHbaseConnectionManager(quorum, port, znode);
		hbaseConnectionManager.setup();

		HbaseServiceImpl service = new HbaseServiceImpl();
		service.setHbaseConnectionManager(hbaseConnectionManager);

		HTableInterface htable = service.getTable("NB_LOG_API_WS");
		System.out.println(">>>> : " + htable.getName().getNameAsString());
		System.out.println(">>>> : " + htable.getName().getNamespaceAsString());
		System.out.println(">>>> : " + htable.getName().getQualifierAsString());

		hbaseConnectionManager.destroy();

	}
}
