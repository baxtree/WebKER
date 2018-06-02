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
				FileInputStream file = new FileInputStream("f:\\small\\small.owl");//������������̫��ʹ
				in = new InputStreamReader(file, "UTF-8");// ��������
				model.read(in, null);
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("�޷���Դrdf��������ֹ");
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
				in = new InputStreamReader(file, "UTF-8");// ��������
				
				
				//RDFReader rr = model.getReader() ;//read method 1
				//rr.read(model, in, "") ;
				
				//model.read(in,"") ;//read method2
				
				model2.read(in, null);
				//model = model.union(model2) ;//ԭʼ�ĺϲ�API
				/*
				 * ���������Լ��ĺ���
				 */
				Union(model,model2) ;
				in.close();
			} catch (FileNotFoundException e) {
				System.out.println("�޷�����rdf��������ֹ");
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
		 * ע��: ����Check��ǰ����des��û�к�t��ȫ���ϵ�statement
		 * 
		 * MERGE_METHOD_ADD ��������Դ�����ж������ֵ
		 * MERGE_METHOD_REPLACE ��������Դֻ����һ������ֵ
		 * ������src��des�е�ֵ��ͻ
		 * MERGE_METHOD_INVALID ������src�е���Դ����Ч��
		 */
		public final int Check(Model des, Statement t)
		{
			Resource res = t.getSubject() ;
			Property pro = t.getPredicate() ;
			RDFNode rnObj = t.getObject() ;
			//����Ϊʾ��:�����������ĳ�ֵ�ֵ����,������ԭ֪ʶ������ͬ����S��P,���滻OΪ��ֵ
			if ( des.contains(res, pro) && !rnObj.isAnon() )
			{
				String a = pro.getLocalName() ;
				String b = "�Ƿ�" ;//���Ƿ�Ϊ��,����Ƿ���ͬ,����ֵΪ׼
				if ( a.equals(b) )
					return MERGE_METHOD_REPLACE ;
			}
	        //������ ���� ����ģʽ(������������Ų�ͬ,������ͬ����Ԫ����ܲ���ȫ��ͬ,����ʱӦ����ͬ����,������)
			if ( sameButHasDifAnon(res, pro, rnObj, des) )
				return MERGE_METHOD_DISCARD ;
			else
			    return MERGE_METHOD_ADD ;
		}
		/*
		 * ����: ������ͬ����Ԫ�������в�ͬ��ŵ�����������ͬ.
		 * 
		 * ע��:�����ڱȽ�S,P,Oʱ������Ǳ����String,��Ϊ�������ǳ���URI,�в���ȫ��ͬ�ĵط�.
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
		 * des����ԭ�е�֪ʶ
		 * src������֪ʶ
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
						//ֻ���des����һ�����ϵĽڵ�,���һ��statement,����ɾ��,����src���滻
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

