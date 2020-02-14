package com.paul.easytodo.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.paul.easytodo.R;

import java.util.Date;
import java.util.Random;

public class WordsUtil {
    private static final String[] morning_words={"早上好，快点开启活力满满的新一天吧！","今天也要加油！早安","早上好，要保持一天的好心情哦","早上好，为了美好的未来继续奋斗","别忘记吃早餐哦~"};
    private static final String[] am_words={"上午好，高效专注！","冲冲冲！","喝一杯咖啡，活力一整天~","有没有期待过更美好的明天呢？"};
    private static final String[] noon_words={"中午吃点好的！","中午要多吃点哦~","吃完休息一会吧","中午好~今天吃了啥"};
    private static final String[] afternoon_words={"下午的美好时光也要保持高效率哦","下午了，也可以出去走一走","坚持就是胜利呀","冲冲冲！","下午好~继续加油"};
    private static final String[] evening_words={"晚上好~","有没有想过，梦想实现的那一天","相信自己，你一定可以的","夜深了也要早点休息哦~","别太矫情，你是最胖的！！！","要保持前进的动力！"};
    public static final String[] comeon_words={"总会有难熬的日子，但你会感谢今日努力拼命的自己，你想要的就是你的未来；世界上所有的惊喜和好运，都是你累积的人品和善良","生活是自己的，你选择怎样的生活，就会成就怎样的你。与其抱怨这个世界不美好，不如用自己的努力，争取更多的美好和幸运"
    ,"爱情友情都一样，等你足够强大，相应的圈子会主动来吸纳你，无需自作悲悯，更无须刻意讨好，你需要的是自我丰盈与精彩","以前以为坚持就是永不动摇，现在才明白，坚持是犹豫着、退缩着、心猿意马着，但还在继续往前走",
    "不开口，没有人知道你想要什么；不去做，任何想法都只在脑海里游泳；不迈出脚步，永远找不到你前进的方向。其实你很强，只是懒惰帮了你倒忙","一件事无论太晚或者太早，都不会阻拦你成为你想成为的那个人，这个过程没有时间的期限，只要你想，随时都可以开始",
    "世界上根本没有感同身受这回事，针不刺到别人身上，他们就不会知道你有多痛，人终归要靠自己坚强起来","如果你不够优秀，人脉是不值钱的，它不是追求来的，而是吸引来的。只有等价的交换，才能得到合理的帮助",
    "在事情没有成功之前，不要在人前谈及任何有关的计划和想法。世界不会在意你的自尊，只是你的成就。在你没有成就之前，切勿强调自尊","人这一生需要用钱捍卫尊严的地方实在太多了，在你还没赚到足够令自己安心的钱之前，请多点努力少点矫情",
    "每一个清晨，记得鼓励自己。没有奇迹，只有你努力的轨迹；没有运气，只有你坚持的勇气","无论你今天要面对什么，既然走到了这一步，就坚持下去，给自己一些肯定，你比自己想象中要坚强",
    "如果你不想苦一辈子，就要先苦一阵子。你现在有什么样的付出，将来你的人生就会呈现什么样的风景。每一个你所期待的美好未来，都必须依靠一个努力的踏实现在","别人可以替你开车，但不能替你走路；可以替你做事，但不能替你感受。人生的路要靠自己行走，成功要靠自己去争取。天助自助者，成功者自救",
    "你不能是一只橙子，把自己榨干了汁就被人扔掉。你该是一棵果树，春华秋实，年年繁茂","一个人的时候多一点努力，才能让自己的爱情少一点条件，多一点纯粹","听我的，一定要好好生活，一定要努力赚钱，到时候你就会发现，人生熠熠发光的时候，哪儿还有时间去抱怨、去矫情，去患得患失、去留恋、去感伤",
    "如果一个人没有能力帮助他所爱的人，最好不要随便谈什么爱与不爱。当然，帮助不等于爱情，但爱情不能不包括帮助","选择了方向与路途时，就不要抱怨，一个人只有承担起旅途风雨，才能最终守得住彩虹满天",
    "当你越来越漂亮时，自然有人关注你。当你越来越有能力时，自然会有人看得起你","你不要感到失望，生命中遇见着的跌宕起伏，荡气回肠，都是为了最美的平凡","刻提醒自己：不要因不了解便去拒绝，不要因不喜欢而听不见，不要因自以为而下定论，不要因大多数都那么认为就觉得那便是正确的",
    "生就如同一场长跑，我们从同一个起点出发，有的人目光灼灼一路狂奔，有些人不紧不慢匀速前进，有的人偶尔停下来，看看路边的花","不管发生什么事，都请安静且愉快地接受人生，勇敢地、大胆地，而且永远地微笑着。"};
    /**
     * 根据时间段获取问候语
     * */
    public static String getWordsByTime(){
        int timepart=DateUtil.getCurTimePart();
        Random random=new Random();
        if(timepart==0){
            return morning_words[random.nextInt(morning_words.length)];
        }else if(timepart==1){
            return am_words[random.nextInt(am_words.length)];
        }else if(timepart==2){
            return noon_words[random.nextInt(noon_words.length)];
        }else if(timepart==3){
            return afternoon_words[random.nextInt(afternoon_words.length)];
        }else {
            return evening_words[random.nextInt(evening_words.length)];
        }
    }
    /**
     * 获取励志话语
     * */
    public static String getWordsByRandom(Context context){
        String datebasename="WordsData";
        String flag="wordsDate";
        String words_flag="wordsContent";
        SharedPreferences sharedPreferences=context.getSharedPreferences(datebasename,Context.MODE_PRIVATE);
        if(DateUtil.getCurDate().equals(sharedPreferences.getString(flag,""))){
            return sharedPreferences.getString(words_flag,"");
        }else{
            Random random=new Random();
            SharedPreferences.Editor editor=sharedPreferences.edit();
            String content=comeon_words[random.nextInt(comeon_words.length)];
            editor.putString(flag, DateUtil.getCurDate());
            editor.putString(words_flag,content);
            editor.apply();
            return content;
        }
    }
}
