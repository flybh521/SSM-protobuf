

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.HttpClients;

import com.google.protobuf.InvalidProtocolBufferException;
import com.humanvoyage.constant.GameError;
import com.humanvoyage.handle.MessageHandle;
import com.humanvoyage.pb.BasePb.Base;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRq;
import com.humanvoyage.pb.UseInfoPb.GetUseInfoRs;

public class HeTest {
	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			final int a=i;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Base.Builder builder = Base.newBuilder();
					builder.setCommand(101);
					builder.setIndex(a);
					builder.setCode(GameError.OK.getCode());
					GetUseInfoRq.Builder rsBuilder = GetUseInfoRq.newBuilder();
					rsBuilder.setSid(1);
					builder.setExtension(GetUseInfoRq.ext, rsBuilder.build());
					try {
						//HttpHelper.sendMsgToGame("http://localhost:4040/hserver/account.do", builder.build());
						//HttpClientUtils.sendHttpPostByStream("http://localhost:4040/hserver/account.do",  builder.build().toString());
						
						
						
						byte[] content = builder.build().toByteArray();  
//						HttpClient client = HttpClients.createDefault();
//						PostMethod postMethod = new PostMethod(URL);    
						//postMethod.addRequestHeader("Content-Type", "application/octet-stream;charset=utf-8");  
						
						
						
						 HttpClient httpClient = new HttpClient();
					        PostMethod postMethod = new PostMethod("http://localhost:4040/hserver/account.do");
					        postMethod.setRequestHeader("Content-Type","application/octet-stream;charset=utf-8");
						 postMethod.setRequestEntity(new ByteArrayRequestEntity(content ));    
						 httpClient.executeMethod(postMethod);    
						 byte[] responseBody = postMethod.getResponseBody();
						 Base base = Base.parseFrom(responseBody, MessageHandle.PB_EXTENDSION_REGISTRY);
						 System.out.println(base.toString());;
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();;
		}
		
		
		
		
	}
}
