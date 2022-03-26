# studyJapanese


https://user-images.githubusercontent.com/71477375/160128671-bacfbc2f-b8c0-4007-bf2f-edd7c1ca8537.mp4



<h2>first</h2>

create github

<h2>BottomNavigationBar</h2>

<p>res에 android resource directory 선택 후 menu 폴더 생성</p>

- NavigationBar 리소스 이름은 <b>bottom_menu</b>로 지정

```java
//Navigation에 들어가는 item 지정, title이 보임, item 개수에 따라 개수가 정해짐
<item
        android:id="@+id/first_tab"
        app:showAsAction="always"
        android:enabled="true"
        android:icon="@drawable/write"
        android:title="Music" />
```

- menu에서 설정한 NavigatiomBar 가져오기

```java
//상위 Layout이 RelativeLayout
//bottom_menu 
<com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottom_menu"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:background="#345E86"
        app:itemIconTint="#FFFFFF"
        app:itemTextColor="#f6d170"
        app:labelVisibilityMode="labeled"
        app:menu="@menu/bottom_menu"/>
```

- BottomNavigationBar적용 

![1111](https://user-images.githubusercontent.com/71477375/157703614-3f25027e-5863-49f0-b76c-c286557fde95.PNG)

<h2>BottomNavigationBar에 Fragment설정</h2>

- 다음과 같이 BlankFragment생성

![image](https://user-images.githubusercontent.com/71477375/157809868-413e3f40-9907-4925-971f-ee7805ae66a3.png)

<p>일반 class가 아닌 Fragment로 생성해야함</p>

- Fragment내의 기본 소스 설정

```java
public class MusicFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //R.layout.X는 해당하는 Fragment의 xml의 이름을 가져오는 것
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);

        return rootView;
    }
}
```
        
- Main에서 설정한 Fragment가져오기


```java
MusicFragment musicFragment;
musicFragment = new MusicFragment();

//어플 시작 시 초기 Fragment화면 설정
getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();

//bottom_mune의 xml을 가져옴.
BottomNavigationView bottom_menu = findViewById(R.id.bottom_menu);
bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
        //bottom_menu의 item의 id를 가져온 것.
        case R.id.first_tab:
                //item이름이 R.id.first_tab인 경우 musicFragment인 Framgent로 교체
                //R.id.container는 activity_main에 있는 FrameLayout의 id이름임.
                getSupportFragmentManager().beginTransaction().replace(R.id.container, musicFragment).commit();
                return true;
        case R.id.second_tab:
                //item이름이 R.id.second_tab인 경우 lyricsFragment인 Fragment
                getSupportFragmentManager().beginTransaction().replace(R.id.container, lyricsFragment).commit();
                return true;
                }
        return false;
        }
});
```

<h2>다른 xml의 View를 가져오는 inflater</h2>

<p>inflater는 액티비티 간의 전환이 아닌, 액티비티 내의 특정 LinearLayout이 있는 공간만 바꾸고 싶을 때 사용</p>

- inflater 기본 코드

```java
LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//after_login이라는 이름의 xml을 일단 디폴트로 설정
//before_after_login이라는 activity_main.xml내의 LinearLayout의 이름을 가져와서, before_login.xml을 
inflater.inflate(R.layout.after_login, before_after_login, true);
```

<p>inflater로 다른 xml의 View가져와 사용하기</p>
<p>위에 LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);가 설정되어 있으면 그대로 사용할 수 있다.</p>

```java
TextView name = (TextView)findViewById(R.id.afterLoginTextView);
name.setText(getName + "님 환영합니다.");
```

<p>로그인 전 before와 후인 after를 xml로 만들어 둔 뒤, class내에서 로그인 성공 시 after로 바꿈</p>

<h2>내부 Room을 이용한 DB</h2>

- gradle의 dependencies에 다음과 같은 ROOM이용을 위한 소스 추가

```java
implementation 'androidx.room:room-runtime:2.3.0'
```

<p>만약 빌드 시 에러가 난다면 다음 implementation 위치에 annotationProcessor 'androidx.room:room-compiler:2.3.0'추가</p>

- User에 대한 정보를 get, set하는 class

<p>다음과 같이 @Entity를 추가함으로 User를 DB로 사용하겠다는 뜻이고, 각각 get, set을 만들어 줘야함</p>
<p>여기에 들어가는 userxxx들은 전부 DB에 들어가는 객체 값들</p>
        
```java
@Entity
public class User {
    //id 값은 자동적으로 1씩 더해지도록 하는 것이 PrimaryKey이다.
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private String userID;
    private String userPass;
    private String userName;
    private String userAge;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
```

- UserDao에서는 User데이터 베이스의 삽입, 갱신, 삭제, Query등을 만들어서 DB를 조작하는 인터페이스

<p>Dao는 삽입, 갱신 등을 말함</p>
<p>@Dao를 추가하고 @Insert등을 만들고 밑에 메소드를 만듬</p>
<p>class가 아닌 interface로 만들어야함</p>

```java
@Dao
public interface UserDao {
    //삽입
    @Insert
    void setInsertUser(User user);

    //수정
    @Update
    void setUpdateUser(User user);

    //삭제
    @Delete
    void setDeleteUser(User user);

    //DB 요청 명령문
    @Query("SELECT * from User")
    List<User> getUserAll();

}
```

- UserDatabase는 추상으로 만들어서, @Database에 아까 User.class에서 만든 entities를 가져온다. version = 1

<p>추상으로 만들고 RoomDatabase를 상속받는다.</p>
<p>갱신 삭제 등을 하는 UserDao를 가져와서 추상메소드로 만든다.</p>

```java
@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
```

- signUpActivity클래스에서 userDao를 이용해 삽입, 갱신, 삭제를 함

<p>signUPActivity클래스에서 만들어둔 User를 사용하기 위해서는 Dao 인스턴스를 만들어야함.(User.table에 정보를 건들 수 있도록) </p>

```java
public class SignUpActivity extends AppCompatActivity {
    //ROOM이용
    private UserDao mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        //UserDatabase를 이용한 db생성인데 tmddmddnjs_db는 table이름.
        UserDatabase database = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "tmddmddnjs_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        //uUserDao는 UserDao 인터페이스를 이용해, 삽입 갱신등을 함. 예시는 아래 삽입, 수정, 삭제로 확인해보자.
        mUserDao = database.userDao();
    }
}
```

- 위 uUserDao를 이용해 삽입, 갱신, 삭제를 해보자

<p>삽입</p>

```java
User uInsert = new User();
uInsert.setName("정승원");
uInsert.setAge("25");
uInsert.setPhoneNumber("010-1111-2222");

mUserDao.setInsertUser(uInsert);
```

<p>수정</p>

```java
//데이터 수정
User uUpdate = new User();
uUpdate.setId(1); //바꾸고 싶은 id 지정
uUpdate.setName("정유설");
uUpdate.setAge("24");
uUpdate.setPhoneNumber("010-1111-0000");

mUserDao.setUpdateUser(uUpdate);
```

<p>삭제</p>

```java
//원하는 id 1개만 삭제
User uDelete = new User();
uDelete.setId(1);
mUserDao.setDeleteUser(uDelete);
```

<p>한번에 다 삭제</p>

<p>UserDao에 다음과 같은 소스 추가</p>

```java
@Query("DELETE FROM User ")
    void deleteAll();
```

<p>조회</p>

```java
//User에 있는 List를 가져오는 것
List<User> userList = mUserDao.getUserAll();
//i는 id의 값
for (int i = 0; i < userList.size(); i++){
        Log.d("Test", userList.get(i).getName() + " "
        +userList.get(i).getPhoneNumber());
}
Log.d("test", userList.get(1).getName()+"");
```

<h2>ROOM 회원가입 시 아이디 중복 확인</h2>

<p>checkId로 db의 모든 UserId값을 확인하면서 id와 동일한 값이 있는지 확인해본다.</p>

```java
List<User> userList = mUserDao.getUserAll();
for (int i = 0; i < userList.size(); i++){
        String checkId = userList.get(i).getUserId() + "";
        if(checkId.equals(id + "")){
                Toast.makeText(SignUpActivity.this,"아이디가 중복되었습니다.", Toast.LENGTH_SHORT).show();
                return;
        }
}
```

<h2>intent로 activity간 값 교환</h2>

- 넘겨줄 때

```java
Intent intent = new Intent(LoginActivity.this, MainActivity.class);
putName = userList.get(i).getUserName() + "";
intent.putExtra("이름", putName);
startActivity(intent);
```

- 받을 때

```java
Intent intent = getIntent();
String getName = intent.getExtras().getString("이름");
```

<h2>musicFragment에 DB에 있는 아티스트명, 음악명을 RecyclerView로 나타내기</h2>

- RecyclerView 생성 방법

<p><b>1. music_recyclerview_item-list.xml이라는 실제 화면에 보여질 xml을 만든다.</b></P>

```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">

    <TextView
        android:layout_marginTop="20dp"
        android:layout_marginLeft="20dp"
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:id="@+id/item_list_textView1"/>

    <TextView
        android:layout_marginBottom="20dp"
        android:layout_marginLeft="20dp"
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:id="@+id/item_list_textView2"/>

    <View
        android:layout_width="match_parent"
        android:layout_height="2dp"
        android:background="#000000"/>


</LinearLayout>
```

<p><b>2. musicFragment.xml에 RecyclerView생성</b></p>

<p><b>3. MusicRecyclerViewitem.java를 생성한다</b></p>
<p>1에서 생성한 textView 2개에 넣을 데이터를 만든다. get, set을 이용해 데이터 교환 </p>

```java
public class MusicRecyclerViewItem {
    private String main;
    private String sub;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }
}
```

<p><b>4. musicAdapter.java를 생성한다</b></p>
<p>viewholder를 사용하여 1의 xml의 view를 가져와서 아이템 뷰를 저장한다</p> 

```java
public class musicAdapter extends RecyclerView.Adapter<musicAdapter.musicHolder> {
    ArrayList<MusicRecyclerViewItem> list;

    public musicAdapter(ArrayList<MusicRecyclerViewItem> data){
        list = data;
    }

    //inflater를 통해 1에서 설정한 music_recyclerview_item_list.xml을 가져와 musicAdapter holder 설정
    @NonNull
    @Override
    public musicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.music_recyclerview_item_list, parent, false);
        musicAdapter.musicHolder mh = new musicAdapter.musicHolder(view);
        return mh;
    }

    //해당 position에 있는 값들을 get해서 item_list_textView에 setText한다
    @Override
    public void onBindViewHolder(@NonNull musicHolder holder, int position) {
        MusicRecyclerViewItem item = list.get(position);
        holder.item_list_textView1.setText(item.getMain());
        holder.item_list_textView2.setText(item.getSub());
    }

    //실제 list의 길이만큼 return 받는다
    @Override
    public int getItemCount() {
        return list.size();
    }

    //아이템 뷰를 저장하는 뷰홀더 클래스스
   class musicHolder extends RecyclerView.ViewHolder{
        TextView item_list_textView1;
        TextView item_list_textView2;
        public musicHolder(@NonNull View itemView) {
            super(itemView);
            item_list_textView1 = itemView.findViewById(R.id.item_list_textView1);
            item_list_textView2 = itemView.findViewById(R.id.item_list_textView2);
        }
    }
}
```

<p><b>5. musicFragment에서 데이터를 집어넣는다.</b></p>
<p>music.db에 있는 데이터들을 각각 main과 sub에 집어 넣는다.</p>
 
```java
//리스트 뷰 사용하기
RecyclerView mRecyclerView;
musicAdapter musicAdapter;
ArrayList<MusicRecyclerViewItem> list;
String artistName, musicName;

public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);

        //RecyclerView 생성
        mRecyclerView = rootView.findViewById(R.id.music_recyclerView);
        list = new ArrayList<>();

        musicAdapter = new musicAdapter(list);
        mRecyclerView.setAdapter(musicAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        //musicDB사용
        musicDatabase database = Room.databaseBuilder(rootView.getContext(), musicDatabase.class, "music_db")
                .fallbackToDestructiveMigration()   //스키마 버전 변경 가능
                .allowMainThreadQueries()           //메인 스레드에서 DB에 IO를 가능하게 함
                .build();

        mmusicDao = database.musicDao();

        //musicdb의 내용 이름들을 가져와서
        List<music> musicList = mmusicDao.getmusicAll();
        for(int i = 0; i<musicList.size(); i++) {
            artistName = musicList.get(i).getArtistName() + "";
            musicName = musicList.get(i).getMusicName() + "";
            //munuItems에 넣은 뒤 list에 넣는다
            addItem(artistName, musicName);
        }

        musicAdapter.notifyDataSetChanged();

        return rootView;
    }
    private void addItem(String main, String sub){
        MusicRecyclerViewItem item = new MusicRecyclerViewItem();
        item.setMain(main);
        item.setSub(sub);

        list.add(item);
    }
```

- 리사이클 뷰에 보여지는 db 속 내용

![실제 db내용](https://user-images.githubusercontent.com/71477375/158541455-672c6337-36af-4128-9af4-c56c95fb427b.PNG)

- 실제 화면 상에 보이는 리사이클 뷰

<p>artistName이 main이고, 아래에 musicName이 sub이다.</p>

![노래](https://user-images.githubusercontent.com/71477375/158541453-a77e496d-67a4-4f3b-b3de-c4a65fad1794.PNG)

<h2>RecyclerView의 click event를 다른 액티비티나 프래그먼트에서 실행하기</h2>

<p>musicAdapter에서 musicHolder아래에 다음과 같이 추가</p>

```java
//다른 액티비티나 프래그먼트에서 리사이클 뷰를 클릭했을 시 이벤트 발생
itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        int pos = getAdapterPosition();
        if (pos != RecyclerView.NO_POSITION) {
                //다른 액티비티나 프래그먼트에서 실행하기 위해 onItemClick를 호출한다
                mListener.onItemClick(v, pos);
                }
        }
});
```

<p>추가로 제일 똑같이 musicAdapter아래에 OnItemClickListener, 변수, setOnItemClickListener 추가</p>

```java
public class musicAdapter extends RecyclerView.Adapter<musicAdapter.musicHolder> {
//다른 액티비티나 프래그먼트에서 리사이클 뷰를 클릭했을 시 이벤트 발생
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;

    // OnItemClickListener 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.mListener = listener;
    }
}
```

- 만약 그냥 그 페이지에서만 버튼 이벤트를 만들고 싶으면

```java
//다른 액티비티나 프래그먼트에서 리사이클 뷰를 클릭했을 시 이벤트 발생
itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        int pos = getAdapterPosition();
        if (pos != RecyclerView.NO_POSITION) {
                //여기에 그냥 그대로 사용하면 된다.
                }
        }
});
```

<h2>RecyclerView에서 특정 item의 position값이 아닌 이름을 가져오고 싶을 때</h2>

- RecyclerView에서 item값 가져오기
- 다른 fragment로 이동할 때

<p>먼저 musicAdapter에서 MusicRecyclerViewItem에서 position의 item값을 가져오기 위해 다음과 같이 추가</p>

```java
public MusicRecyclerViewItem getItem(int position){
        return list.get(position);
}
```

<p>그 후 musicFragment에서 RecyclerView의 item값 가져오기 위해서 위의 getItem을 이용하고 및 클릭시 다른 fragment로 이동하고 싶은 경우</p>
<p>여기서는 item.getSub()의 이름을 가져온다</p>

```java
//recyclerView click event
lyricsFragment = new LyricsFragment();
musicAdapter.setOnItemClickListener(new musicAdapter.OnItemClickListener() {
@Override
public void onItemClick(View v, int pos) {
        //musicAdapter에서 getItem을 통해 MusicRecyclerViewItem에 있는 getSub()로 sub이름인 노래명을 가져온다.
        //MusicRecyclerViewItem의 getSub()에는 position의 노래명이 들어가 있음
        MusicRecyclerViewItem item = musicAdapter.getItem(pos);
        Toast.makeText(getContext(), item.getSub()+"", Toast.LENGTH_SHORT).show();
                
        //리사이클 뷰의 특정 아이템 클릭시 lyricsFragment로 이동
        getFragmentManager().beginTransaction().replace(R.id.container, lyricsFragment).commit();
        }
});
```

<h2>Fragment 간 데이터 교환 Bundle</h2>

```java     
//framgent간 데이터 교환(보내는 쪽)
Bundle result = new Bundle();
result.putString("musicName", item.getSub()+"");
lyricsFragment.setArguments(result);
        
//fragment간 데이터 교환(받는 쪽)
Bundle result = getArguments();
if(result != null){
        musicFragment_musicName = result.getString("musicName");
        lyricsMusicName.setText("노래명 : " + musicFragment_musicName);
}
```

<h2>Lirics에서 가사 나타내기</h2>

<p>musicFragment에서 받은 musicFragment_musicName과 lyricsFragment에서 for문으로 db를 보고 같은 이름의 음악명이 있으면 해당 명의 가사를 나타낸다.</p>

```java     
List<lyrics> lyricsist = mlyricsDao.getlyricsAll();
for (int i = 0; i < lyricsist.size(); i++) {
        musicName = lyricsist.get(i).getMusicName()+"";
        //bundle로 받아온 musicFragment_musicName lyrics.db의 musicName이 같으면
        //lyrics.db의 해당 lyrics을 가져옴.
        if(musicName.equals(musicFragment_musicName)){
                lyricsText.setText(lyricsist.get(i).getMusicLyrics() + "");
        }
}
```

- recyclerView에 있는 item을 클릭하면 해당 item을 가져와서 넘긴다.

![노래제목](https://user-images.githubusercontent.com/71477375/158579939-3fbe1043-85cc-4add-b1f7-f85e1b0bbd49.PNG)

- musicFragment에서 받은 musicName과 일치하는 음악 이름의 가사를 나타낸다.

![클릭시](https://user-images.githubusercontent.com/71477375/158579934-6fee1558-9823-4fbb-b6e5-f0dd2b763aab.PNG)

<h2>AlertDialog생성</h2>

- 다음의 그림의 LyricsFragment의 "단어추가하기" 버튼을 클릭 시 만들어 놓은 MyAlterDialog를 가져온다.

![1](https://user-images.githubusercontent.com/71477375/159693929-737b263d-84d0-4af8-ab71-0fa1f6b92ab7.PNG)

```java     
lyricsAddButton.setOnClickListener(new View.OnClickListener() {
        Override
        public void onClick(View v) {
                MyAlterDialog();
        }
});
```

- customDialog의 xml (plus_word.xml)

![2](https://user-images.githubusercontent.com/71477375/159693935-1ea83141-5c80-4ab8-abda-a54c9b59801e.PNG)

- MyAlterDialog 소스

```java     
public void MyAlterDialog(){
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.plus_word, null);

        //inflater로 plus_word.xml을 불러옴
        ad.setView(view)
                .setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        //입력 받은 데이터는 Word.db에 저장
                        try {
                            EditText plusWordAddWord = (EditText)view.findViewById(R.id.plusWordAddWord);
                            EditText plusWordAddMean = (EditText)view.findViewById(R.id.plusWordAddMean);

                            //텍스트 받아와서
                            String AddWord = plusWordAddWord.getText().toString();
                            String AddMean = plusWordAddMean.getText().toString();
                            //Word.db에 다이어로그에 입력되었던 단어를 insert
                            Word uInsert = new Word();
                            uInsert.setWordWord(AddWord + "");
                            uInsert.setWordMean(AddMean + "");
                            mWordDao.setInsertWord(uInsert);

                            dialog.dismiss();

                            Toast.makeText(getContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        //중간에 insert가 안되었다면
                        catch (Exception e){
                            Toast.makeText(getContext(), "추가가 안되었습니다.", Toast.LENGTH_SHORT).show();
                            Log.d("noInputData", e+"");
                        }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        //취소 버튼 클릭 시 아무것도 안함
                        dialog.dismiss();
                        }
                });
        ad.show();
}
```

<h2>TextView 클릭으로 뒤집기</h2>

- fragment_study.xml에 include layout을 만들어서 다른 xml을 올려놓는다.

```java     
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".Fragment.StudyFragment">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <include
            android:id="@+id/include"
            layout="@layout/card_view" />

    </LinearLayout>

</FrameLayout>
```

- include에 넣을 xml인 card_view를 만들어준다.(그림은 card_view를 include layout에 넣었을 때)

![앞](https://user-images.githubusercontent.com/71477375/160102041-719bb2f2-8328-4dcc-a550-631eda93a775.PNG)

```java     
<LinearLayout
android:layout_width="wrap_content"
android:layout_height="wrap_content">

        <TextView
        android:id="@+id/cardTextView"
        android:layout_width="120dp"
        android:layout_height="200dp"
        android:background="@color/design_default_color_secondary"
        android:textSize="26sp"
        android:textStyle="bold" />
        
</LinearLayout>
```

- StudyFragment.java소스 (클릭마다 앞, 뒤가 바뀐다.)

![앞](https://user-images.githubusercontent.com/71477375/160102041-719bb2f2-8328-4dcc-a550-631eda93a775.PNG)
![뒤](https://user-images.githubusercontent.com/71477375/160102044-a79efd14-11ff-40c3-b6ac-d42cd17fa7c5.PNG)

```java     
public class StudyFragment extends Fragment {
    //textView 뒤집기
    private TextView clickTextView;
    private boolean viewClickCheck;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_study, container, false);

        //inclue layout의 textView 뒤집기
        clickTextView = rootView.findViewById(R.id.cardTextView);
        clickTextView.setOnClickListener(new View.OnClickListener() {
            //텍스트를 누르면 Y축 90식 회전을 시키는데
            @Override
            public void onClick(View v) {
                clickTextView.animate().withLayer()
                        .rotationY(90) //Y축 회전
                        .setDuration(150) //시간
                        .withEndAction(new Runnable() {
                            //반복해서 누를 때 마다 스레드로 계속해서 바꿔주는 것
                            @Override
                            public void run() {
                                //앞
                                if (!viewClickCheck) {
                                    clickTextView.setText("여기는 앞!!");
                                    viewClickCheck = true;
                                }
                                //뒤
                                else {
                                    clickTextView.setText("여기는 뒤!!");
                                    viewClickCheck = false;
                                }
                                clickTextView.setRotationY(-90); //Y축 복구
                                clickTextView.animate().withLayer()
                                        .rotationY(0)
                                        .setDuration(250) //시간
                                        .start();
                            }
                        }).start();
            }
        });

        return rootView;
    }
}
```


- Next버튼을 누르면 다음 단어가 보이도록 설정하였다.

```java     
private void buttonNextPrev() {
        studyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    clickTextView.animate().withLayer()
                            .rotationY(90)
                            .setDuration(150) //시간
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                        clickTextView.setRotationY(-90);
                                        clickTextView.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250)
                                                .start();
                                        viewClickCheck = true;
                                    }
                                    //DB 범위 초과 시 돌아오도록
                                    catch (Exception e){
                                        i--;
                                        clickTextView.setText("단어 : " + wordList.get(i).getWordWord() + "");
                                        clickTextView.setRotationY(-90);
                                        clickTextView.animate().withLayer()
                                                .rotationY(0)
                                                .setDuration(250)
                                                .start();
                                        viewClickCheck = true;
                                        Toast.makeText(getContext(), "마지막 페이지.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }).start();
                    i++;
            }
        });
```


- 기존의 화면에서 Next를 눌렀을 시

![3](https://user-images.githubusercontent.com/71477375/160124917-6a59e7bf-3383-465f-972b-6b5279f55bec.PNG)
![1](https://user-images.githubusercontent.com/71477375/160126268-1dd08390-7327-400f-9efd-4151ed08ec06.PNG)


- 데이터베이스의 범위 초과 시

![4](https://user-images.githubusercontent.com/71477375/160125744-e95f17ac-fabc-4044-b03d-7385336a0f0b.PNG)
