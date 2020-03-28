package com.krzem.precise_math;



import java.lang.Math;



public class PreciseNumber{
	private String v;
	private boolean neg;



	public PreciseNumber(){
		this._set(0);
	}
	public PreciseNumber(Object v){
		this._set(v);
	}



	public PreciseNumber set(Object o){
		this._set(o);
		return this;
	}



	public PreciseNumber add(Object o){
		PreciseNumber n=new PreciseNumber(o);
		if (n.neg==false&&this.neg==false){
			this.v=this._add(this.v,n.v);
		}
		else if (n.neg==true&&this.neg==false){
			String b=this._bigger(n);
			String s=this._smaller(n);
			if (b.equals(n.v)){
				this.neg=true;
			}
			this.v=this._sub(b,s);
		}
		else if (n.neg==false&&this.neg==true){
			String b=this._bigger(n);
			String s=this._smaller(n);
			if (b.equals(n.v)){
				this.neg=false;
			}
			this.v=this._sub(b,s);
		}
		else if (n.neg==true&&this.neg==true){
			this.v=this._add(this.v,n.v);
		}
		this._validate();
		return this;
	}



	public PreciseNumber sub(Object o){
		PreciseNumber n=new PreciseNumber(o);
		if (n.neg==false&&this.neg==false){
			String b=this._bigger(n);
			String s=this._smaller(n);
			if (b.equals(n.v)){
				this.neg=true;
			}
			this.v=this._sub(b,s);
		}
		else if (n.neg==true&&this.neg==false){
			this.v=this._add(this.v,n.v);
		}
		else if (n.neg==false&&this.neg==true){
			this.v=this._add(this.v,n.v);
		}
		else if (n.neg==true&&this.neg==true){
			String b=this._bigger(n);
			String s=this._smaller(n);
			if (b.equals(n.v)){
				this.neg=false;
			}
			this.v=this._sub(b,s);
		}
		this._validate();
		return this;
	}



	@Override
	public String toString(){
		return String.format("%s%s",(this.neg==true?"-":""),this.v);
	}



	private void _set(Object o){
		if (o.getClass().getName().equals("java.lang.Integer")){
			int v=(Integer)o;
			this.neg=(v<0?true:false);
			this.v=Integer.toString(v*(this.neg==true?-1:1));
		}
		else if (o.getClass().getName().equals("java.lang.Long")){
			long v=(Long)o;
			this.neg=(v<0?true:false);
			this.v=Long.toString(v*(this.neg==true?-1:1));
		}
		else if (o.getClass().getName().equals("java.lang.Double")){
			double v=(Double)o;
			this.neg=(v<0?true:false);
			this.v=Double.toString(v*(this.neg==true?-1:1));
		}
		else if (o.getClass().getName().equals("java.lang.Float")){
			float v=(Float)o;
			this.neg=(v<0?true:false);
			this.v=Float.toString(v*(this.neg==true?-1:1));
		}
		else if (o.getClass().getName().equals("java.lang.String")){
			String v=(String)o;
			this.neg=(v.charAt(0)=='-'?true:false);
			this.v=v.substring((this.neg==true?1:0));
		}
		else if (o.getClass().getName().equals("com.krzem.precise_math.PreciseNumber")){
			PreciseNumber v=(PreciseNumber)o;
			this.neg=v.neg;
			this.v=v.v.substring(0);
		}
		else{
			throw new ClassCastException(String.format("Cannot cast %s to com.krzem.precise_math.PreciseNumber",o.getClass().getName()));
		}
		this._validate();
	}



	private void _validate(){
		String nv="";
		boolean dot=false;
		boolean st=false;
		for (int i=0;i<this.v.length();i++){
			if ("0123456789".indexOf(String.valueOf(this.v.charAt(i)))>-1){
				if ((this.v.charAt(i)=='0'&&st==true)||this.v.charAt(i)!='0'){
					nv+=String.valueOf(this.v.charAt(i));
					st=true;
				}
			}
			else{
				if (dot==false&&this.v.charAt(i)=='.'){
					dot=true;
					st=true;
					nv+=".";
				}
			}
		}
		if (nv.length()==0){
			nv="0.0";
		}
		if (nv.indexOf(".")==-1){
			nv+=".0";
		}
		if (nv.indexOf(".")==nv.length()-1){
			nv+="0";
		}
		this.v=nv;
	}



	private String _bigger(PreciseNumber o){
		if (o.v.split("\\.")[0].length()>this.v.split("\\.")[0].length()){
			return o.v;
		}
		if (o.v.split("\\.")[0].length()<this.v.split("\\.")[0].length()){
			return this.v;
		}
		int i=0;
		while (true){
			if (this.v.charAt(i)=='.'){
				i++;
				continue;
			}
			if (i>=this.v.length()){
				return o.v;
			}
			if (i>=o.v.length()){
				return this.v;
			}
			if (Integer.parseInt(String.valueOf(this.v.charAt(i)))>Integer.parseInt(String.valueOf(o.v.charAt(i)))){
				return this.v;
			}
			if (Integer.parseInt(String.valueOf(this.v.charAt(i)))<Integer.parseInt(String.valueOf(o.v.charAt(i)))){
				return o.v;
			}
			i++;
		}
	}



	private String _smaller(PreciseNumber o){
		return (this._bigger(o).equals(this.v)?o.v:this.v);
	}



	private String _add(String a,String b){
		String o="";
		int l1a=a.split("\\.")[0].length();
		int l1b=b.split("\\.")[0].length();
		int l2a=a.split("\\.")[1].length();
		int l2b=b.split("\\.")[1].length();
		if (l1a<l1b){
			for (int i=0;i<l1b-l1a;i++){
				a="0"+a;
			}
		}
		if (l1b<l1a){
			for (int i=0;i<l1a-l1b;i++){
				b="0"+b;
			}
		}
		if (l2a<l2b){
			for (int i=0;i<l2b-l2a;i++){
				a+="0";
			}
		}
		if (l2b<l2a){
			for (int i=0;i<l2a-l2b;i++){
				b+="0";
			}
		}
		int i=Math.max(l1a,l1b)+Math.max(l2a,l2b);
		int c=0;
		while (i>=0){
			if (a.charAt(i)=='.'){
				i--;
				o+=".";
				continue;
			}
			int v=c+Integer.parseInt(String.valueOf(a.charAt(i)))+Integer.parseInt(String.valueOf(b.charAt(i)));
			o+=Integer.toString(v%10);
			c=v/10;
			i--;
		}
		if (c!=0){
			o+=Integer.toString(c);
		}
		String ro="";
		for (int j=o.length()-1;j>=0;j--){
			ro+=String.valueOf(o.charAt(j));
		}
		return ro;
	}



	private String _sub(String a,String b){
		String o="";
		int l1a=a.split("\\.")[0].length();
		int l1b=b.split("\\.")[0].length();
		int l2a=a.split("\\.")[1].length();
		int l2b=b.split("\\.")[1].length();
		if (l1b<l1a){
			for (int i=0;i<l1a-l1b;i++){
				b="0"+b;
			}
		}
		if (l2b<l2a){
			for (int i=0;i<l2a-l2b;i++){
				b+="0";
			}
		}
		int i=Math.max(l1a,l1b)+Math.max(l2a,l2b);
		boolean rm=false;
		while (i>=0){
			if (a.charAt(i)=='.'){
				i--;
				o+=".";
				continue;
			}
			int va=Integer.parseInt(String.valueOf(a.charAt(i)))-(rm==true?1:0);
			int vb=Integer.parseInt(String.valueOf(b.charAt(i)));
			rm=false;
			if (vb<=va){
				o+=Integer.toString(va-vb);
			}
			else{
				va+=10;
				rm=true;
				o+=Integer.toString(va-vb);
			}
			i--;
		}
		String ro="";
		for (int j=o.length()-1;j>=0;j--){
			ro+=String.valueOf(o.charAt(j));
		}
		return ro;
	}
}