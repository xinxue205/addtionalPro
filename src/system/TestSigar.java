/**
 * 
 */
package system;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @author wuxinxue
 * @time 2015-7-21 ÏÂÎç3:13:41
 * @copyright sinobest
 */
public class TestSigar {
	public static void main(String[] args) {
		System.out.println(getSystemInfo());
	}
	
	public static Map getSystemInfo(){
		
		Sigar sigar = new Sigar();
        Mem mem;
        CpuInfo[] cpuInfos;
		try {
			mem = sigar.getMem();
			cpuInfos = sigar.getCpuInfoList();
		} catch (SigarException e) {
//			LOG.error("Get system info occur error:",e);
			return null;
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("mem_total", Long.toString(mem.getTotal()));
		map.put("mem_used", Long.toString(mem.getUsed()));
		map.put("mem_free", Long.toString(mem.getFree()));
		
		if(cpuInfos.length>0){
			map.put("cpu_no", Integer.toString(cpuInfos.length));
			map.put("cpu_model", cpuInfos[0].getModel());
		}
		for (int i = 0; i < cpuInfos.length; i++) {
			CpuInfo ci = cpuInfos[i];
			System.out.println(ci);
		}
		
		OperatingSystem OS = OperatingSystem.getInstance();
		map.put("os_arch", OS.getArch());
		map.put("os_desc", OS.getDescription());
		map.put("os_name", OS.getName());
		
		String currPath = System.getProperty("user.dir");
		File file = new File(currPath);
		map.put("disk_path", currPath);
		map.put("disk_total", Long.toString(file.getTotalSpace()));
		map.put("disk_used", Long.toString(file.getFreeSpace()));
		map.put("disk_free", Long.toString(file.getFreeSpace()));
		
		return map;
	}
}
