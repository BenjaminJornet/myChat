private Handler handler = new Handler();
handler.postDelayed(runnable,100);
private Runnable runnable =new Runnable() {
	@Override
	public void run(){
		foobar();
		handler.postDelayed(this,100);
	}
}