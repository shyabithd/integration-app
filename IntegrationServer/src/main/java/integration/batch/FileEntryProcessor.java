package integration.batch;

import com.google.protobuf.StringValue;
import integration.model.FileEntry;
import integration.model.OutputEntry;
import integration.registry.ConnectionRegistry;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import user.User;
import user.UserServiceGrpc;

public class FileEntryProcessor  implements ItemProcessor<FileEntry, OutputEntry> {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileEntryProcessor.class);

    @Autowired
    ConnectionRegistry connectionRegistry;

    @Override
    public OutputEntry process(FileEntry fileEntry) throws Exception {

        ManagedChannel userChannel = connectionRegistry.getChannel("User");
        ManagedChannel productChannel = connectionRegistry.getChannel("Product");

        UserServiceGrpc.UserServiceBlockingStub userStub = UserServiceGrpc.newBlockingStub(userChannel);
        User.UsersResponse users = userStub.search(User.UserSearchFilter.newBuilder().setEmail(StringValue.newBuilder().setValue(fileEntry.getEmail()).build()).build());
        if (users == null) {
            LOGGER.error("user " + fileEntry.getFull_name() + " retrieved failed, received null response.");
            return null;
        }

        if (users.getUsersList().size() == 0) {
            LOGGER.error("user " + fileEntry.getFull_name() + " retrieved failed, received empty response.");
            return null;
        }

        User.UserResponse user = users.getUsers(0);
        LOGGER.debug(user.getFullName() + " retrieved successfully.");

        OutputEntry outputEntry = new OutputEntry();
        outputEntry.setUserPid(user.getPid());
        outputEntry.setOrderPid(fileEntry.getOrder_id());
        outputEntry.setSupplierPid(fileEntry.getSupplier_id());

        return outputEntry;
    }
}
