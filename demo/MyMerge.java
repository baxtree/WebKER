	package demo;
	import java.io.*;

import com.hp.hpl.jena.rdf.model.* ;

	public class MyMerge {
		public final int MERGE_METHOD_ADD = 1 ;
		public final int MERGE_METHOD_REPLACE = 2 ;
		public final int MERGE_METHOD_DISCARD = 3 ;
		
		public String origFilePath ;
		public String newFilePath  ;
		public String mergedFilePath;
		Model model ;
		Model model2 ;
		
		public MyMerge(String oldontologyfilepath, String addedontologyfilepath, String mergedontologyfilepath) throws FileNotFoundException{
			System.out.print("Merge RDF resourses...");
			model = ModelFactory.createDefaultModel();
			model2 = ModelFactory.createDefaultModel();
			this.origFilePath = oldontologyfilepath;
			this.newFilePath = addedontologyfilepath;
			this.mergedFilePath = mergedontologyfilepath; 
			this.loadModel();
			
			
		}
		
		private void loadModel() throws FileNotFoundException{

			InputStreamReader in;
			try {
				FileInputStream file = new FileInputStream("f:\\small\\small.owl");//变量传进来不太好使
				in = new InputStreamReader(file, "UTF-8");// 处理中文
				model.read(in, null);
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("无法打开源rdf，程序将终止");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}

		}
		
		public void Output() throws FileNotFoundException{
			
			try {
				File file = new File(mergedFilePath);
				FileOutputStream out = new FileOutputStream(file);
				OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
				RDFWriter w = model.getWriter();
				w.write(model, osw, null);

			} catch (Exception e) {
				System.out.println("File cannot be created.");
			}
			System.out.println("Save to "+mergedFilePath);

		}
		public void Merge()throws FileNotFoundException{
			InputStreamReader in;
			try {
				FileInputStream file = new FileInputStream(newFilePath);
				in = new InputStreamReader(file, "UTF-8");// 处理中文
				
				
				//RDFReader rr = model.getReader() ;//read method 1
				//rr.read(model, in, "") ;
				
				//model.read(in,"") ;//read method2
				
				model2.read(in, null);
				//model = model.union(model2) ;//原始的合并API
				/*
				 * 下面是我自己的函数
				 */
				Union(model,model2) ;
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("无法打开新rdf，程序将终止");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
			
			
		}
		public static void main(String[] args)throws FileNotFoundException {
			MyMerge m1 = new MyMerge("F:\\small\\samll.owl", "F:\\600010Ont.owl", "F:\\merged.owl") ;
			m1.Merge() ;
			m1.Output() ;
			MyMerge m2 = new MyMerge("F:\\merged.owl", "F:\\600012Ont.owl", "F:\\merged.owl") ;
			m2.Merge() ;
			m2.Output() ;
			MyMerge m3 = new MyMerge("F:\\merged.owl", "F:\\600015Ont.owl", "F:\\merged.owl") ;
			m3.Merge() ;
			m3.Output() ;
			MyMerge m4 = new MyMerge("F:\\merged.owl", "F:\\6000120Ont.owl", "F:\\merged.owl") ;
			m4.Merge() ;
			m4.Output() ;
			MyMerge m5 = new MyMerge("F:\\merged.owl", "F:\\600030Ont.owl", "F:\\merged.owl") ;
			m5.Merge() ;
			m5.Output() ;
			MyMerge m6 = new MyMerge("F:\\merged.owl", "F:\\600031Ont.owl", "F:\\merged.owl") ;
			m6.Merge() ;
			m6.Output() ;
			MyMerge m10 = new MyMerge("F:\\merged.owl", "F:\\600053Ont.owl", "F:\\merged.owl") ;
			m10.Merge() ;
			m10.Output() ;
			MyMerge m7 = new MyMerge("F:\\merged.owl", "F:\\600068Ont.owl", "F:\\merged.owl") ;
			m7.Merge() ;
			m7.Output() ;
			MyMerge m8 = new MyMerge("F:\\merged.owl", "F:\\600095Ont.owl", "F:\\merged.owl") ;
			m8.Merge() ;
			m8.Output() ;
			MyMerge m9 = new MyMerge("F:\\merged.owl", "F:\\600088Ont.owl", "F:\\merged.owl") ;
			m9.Merge() ;
			m9.Output() ;

		}
		
		/*
		 * below is the "Check" function
		 * 
		 * 注意: 调用Check的前提是des中没有和t完全符合的statement
		 * 
		 * MERGE_METHOD_ADD 表明该资源可以有多个属性值
		 * MERGE_METHOD_REPLACE 表明该资源只能有一个属性值
		 * 并且在src和des中的值冲突
		 * MERGE_METHOD_INVALID 表明在src中的资源是无效的
		 */
		public final int Check(Model des, Statement t)
		{
			Resource res = t.getSubject() ;
			Property pro = t.getPredicate() ;
			RDFNode rnObj = t.getObject() ;
			//以下为示例:如果该属性是某种单值类型,而且在原知识库中有同样的S和P,便替换O为新值
			if ( des.contains(res, pro) && !rnObj.isAnon() )
			{
				String a = pro.getLocalName() ;
				String b = "涨幅" ;//以涨幅为例,如果涨幅不同,以新值为准
				if ( a.equals(b) )
					return MERGE_METHOD_REPLACE ;
			}
	        //处理含有 匿名 结点的模式(由于匿名结点编号不同,所以相同的三元组可能不完全相同,但此时应按相同处理,即丢弃)
			if ( sameButHasDifAnon(res, pro, rnObj, des) )
				return MERGE_METHOD_DISCARD ;
			else
			    return MERGE_METHOD_ADD ;
		}
		/*
		 * 功能: 检验相同的三元组由于有不同编号的匿名结点而不同.
		 * 
		 * 注意:其中在比较S,P,O时候把它们变成了String,因为我们它们除了URI,有不完全相同的地方.
		 */
		public boolean sameButHasDifAnon(Resource r, Property p, RDFNode rn, Model des)
		{
			
			if ( r.isAnon() && rn.isAnon() )
			{
				for ( StmtIterator i = des.listStatements() ; i.hasNext() ; )
				{
					Statement t = i.nextStatement() ;
					if ( t.getPredicate().toString() == p.toString()
						&& t.getObject().isAnon()
						&& t.getSubject().isAnon() )
						return true ;
				}
				return false ;
			}
			if ( r.isAnon() )
			{
				for ( StmtIterator i = des.listStatements(null,p,rn) ; i.hasNext() ; )
				{
					Statement t = i.nextStatement() ;
					if ( t.getSubject().isAnon() )
						return true ;
				}
			}
			if ( rn.isAnon() )
			{
				for ( StmtIterator i = des.listStatements() ; i.hasNext() ; )
				{
					Statement t = i.nextStatement() ;
					if ( t.getPredicate().toString() == p.toString()
						&& t.getObject().isAnon()
						&& t.getSubject().toString() == r.toString() )
						return true ;
				}
			}
			return false ;
		}
		
		/*
		 * below is the implementation of function "Union"
		 * des中是原有的知识
		 * src中是新知识
		 */
		
		public void Union(Model des, Model src)
		{
			//Assume all the data in "src" are valid.
			for ( StmtIterator i = src.listStatements(); i.hasNext() ; )
			{
				Statement t = i.nextStatement() ;
				if ( des.contains(t) )
				{
//					System.out.println("des and src both has:" + t) ;
				}
				else
				{
//					System.out.println("only src has:" + t) ;
					
					switch ( Check(des, t) )
					{
					case MERGE_METHOD_ADD:
						des.add(t) ;
//						System.out.println("Adding.") ;
						break ;
					case MERGE_METHOD_REPLACE:
						Resource res = t.getSubject() ;
						Property pro = t.getPredicate() ;
						//只会从des返回一个符合的节点,组成一个statement,把它删除,并用src的替换
						/*StmtIterator s = des.listStatements(
														 res, 
														 pro, 
														 des.listObjectsOfProperty(res, pro).nextNode()) ;
						des.remove(s.nextStatement()) ;*/
						
						des.remove(des.getProperty(res, pro)) ;
						des.add(t) ;
//						System.out.println("Replacing.") ;
						break ;
					case MERGE_METHOD_DISCARD:
//						System.out.println("Discarding.") ;
						break ;
					default:break ;
					}
				}
				
			}
		}

	}

