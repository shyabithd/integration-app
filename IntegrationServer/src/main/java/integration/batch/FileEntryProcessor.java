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
import product.Product;
import product.ProductServiceGrpc;
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
        User.UserResponse user = userStub.createUser(User.CreateUserRequest.newBuilder()
                .setFullName(StringValue.newBuilder().setValue(fileEntry.getFull_name()).build())
                .setEmail(fileEntry.getEmail())
                .setAddress(User.ShippingAddress.newBuilder()
                        .setAddress(StringValue.newBuilder().setValue(fileEntry.getShipping_address()))
                        .setCountry(StringValue.newBuilder().setValue(fileEntry.getCountry())).build())
                .setPassword(StringValue.newBuilder().setValue(fileEntry.getCredit_card_number()).build())
                .addPaymentMethods(User.PaymentMethod.newBuilder()
                        .setCreditCardNumber(StringValue.newBuilder().setValue(fileEntry.getCredit_card_number()))
                        .setCreditCardType(StringValue.newBuilder().setValue(fileEntry.getCredit_card_type())))
                .build());
        LOGGER.debug(user.getFullName() + " inserted.");

        ProductServiceGrpc.ProductServiceBlockingStub productStub = ProductServiceGrpc.newBlockingStub(productChannel);
        Product.ProductResponse product = productStub.getProductByPid(StringValue.newBuilder().setValue(fileEntry.getProduct_pid()).build());

        Product.ProductResponse productResponse = productStub.createProduct(Product.CreateProductRequest.newBuilder()
                .setName(product.getName())
                .setPid(StringValue.newBuilder().setValue(fileEntry.getEmail()).build())
                .setPricePerUnit(product.getPricePerUnit())
                .build());
        LOGGER.info(productResponse.getName() + " inserted.");

        OutputEntry outputEntry = new OutputEntry();
        outputEntry.setUserPid("sdf");
        outputEntry.setOrderPid(fileEntry.getOrder_id());
        outputEntry.setSupplierPid(fileEntry.getSupplier_id());

        return outputEntry;
    }
}
