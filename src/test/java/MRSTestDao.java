import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myRunningShoes.dao.ShoesDao;
import com.myRunningShoes.dao.UserDao;
import com.myRunningShoes.dao.UserShoesDao;
import com.myRunningShoes.model.Shoe;
import com.myRunningShoes.model.User;
import com.myRunningShoes.model.UserShoes;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dnickerson on 9/15/2016.
 */
public class MRSTestDao {

    final static Logger logger = Logger.getLogger(MRSTestDao.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserShoesDao userShoesDao;

    @Autowired
    private ShoesDao shoesDao;

    @Test
    public void testDao() {

        User user = userDao.getUser(1);
        logger.info("User with id 1 is: " + user.getFirstName() + " " + user.getLastName());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String userStr = gson.toJson(user);
        logger.info(userStr);

        List<UserShoes> shoes = userDao.getShoes(user.getId());
        for(UserShoes userShoes: shoes) {
            logger.info(userShoes.getMake() + " " + userShoes.getModel() + ": " + userShoes.getMiles());
        }

        for(UserShoes userShoes: shoes) {
            if (userShoes.getShoeId() == 1) {
                userShoes.setMiles(userShoes.getMiles() + 10);
                logger.info("After running 10 miles" + userShoes.getMake() + " "
                        + userShoes.getModel() + "has " + userShoes.getMiles() + " miles");
                userShoesDao.saveMiles(userShoes);


            }

        }

        List<Shoe> shoeList = shoesDao.getAvailableShoes();
        for(Shoe shoe: shoeList) {
            logger.info("Shoe make: " + shoe.getMake());
            logger.info("Shoe model: " + shoe.getModel());
            logger.info("Shoe year: " + shoe.getYear());

        }


    }
}
